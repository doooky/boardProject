package kdh.boardproject.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Slf4j
@Component
class TokenProvider( //토큰의 생성, 토큰의 유효성 검증을 담당하는 TokenProvider
        @param:Value("\${jwt.secret}") private val secret: String,
        @Value("\${jwt.token-validity-in-seconds}") tokenValidityInSeconds: Long) : InitializingBean {
    private val tokenValidityInMilliseconds: Long
    private var key: Key? = null
    val log = KotlinLogging.logger {}

    /*
    * application.yml에서 명시한 정보를 주입받아 생성하는 생성자.
    * */
    init {
        tokenValidityInMilliseconds = tokenValidityInSeconds * 1000
    }

    /*
    *
    * */
    override fun afterPropertiesSet() {
        //afterPropertiesSet() 은 InintializingBean 인터페이스의 메소드로
        // BeanFactory에 의해 모든 property 가 설정되고 난 뒤 실행되는 메소드
        // 주로 실행시점의 custom 초기화 로직이 필요하거나 주입받은 property 를 확인하는 용도로 사용됨.
        // ->여기서 사용되는 이유는 빈이 생성이 되고 의존성 주입이 되고 난 후에 주입받은 secret 값을 Base64 Decode 해서 key 변수에 할당하기 위함.

        val keyBytes = Decoders.BASE64.decode(secret)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    /*
     * 토큰 생성 메소드
     * Authentication 객체
     * Spring Security에서 한 유저의 인증 정보를 가지고 있는 객체,
     * 사용자가 인증 과정을 성공적으로 마치면, Spring Security는 사용자의 정보 및 인증 성공여부를 가지고
     * Authentication 객체를 생성한 후 보관한다.
     */
    fun createToken(authentication: Authentication): String { // Authentication 객체의 권한정보를 이용해서 토큰을 생성하는 함수
        val authorities = authentication.authorities.stream()
                .map { obj: GrantedAuthority -> obj.authority }
                .collect(Collectors.joining(",")) //이부분은 stream을 제거해도될듯함. TODO

        val now = Date().time
        val validity = Date(now + tokenValidityInMilliseconds) // 만료시간 설정 > yml에서 가져오는 듯 한데 정확히 확인할 필요 있음. TODO
       return Jwts.builder()
                .setSubject(authentication.name)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact()
    }

    // 토큰에 담겨있는 정보를 이용해 Authentication 객체를 리턴
    fun getAuthentication(token: String?): Authentication { // 토큰을 파라미터로 받아서
        val claims = Jwts // 토큰을 이용해서 클레임을 만들어준다.
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        val authorities: Collection<GrantedAuthority> = Arrays.stream(//클레임에서 권한정보를 빼내서 권한정보를 이용해서 User 객체를 만든다.
                claims[AUTHORITIES_KEY].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())

        // claims과 authorities 정보를 활용해 User (org.springframework.security.core.userdetails.User) 객체 생성
        val principal = User(claims.subject, "", authorities)
        //User객체와, token, Authorities객체 리턴
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String?): Boolean {//토큰 유효성 검증을 수행하는 validateToken
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token) //koen 파싱
            return true
        } catch (e: SecurityException) {//나오는 exception catch
            log.error("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            log.error("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            log.error("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            log.error("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            log.error("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

    companion object {
        private const val AUTHORITIES_KEY = "auth"
    }
}