package kdh.boardproject.config

import kdh.boardproject.jwt.JwtAccessDeniedHandler
import kdh.boardproject.jwt.JwtAuthenticationEntryPoint
import kdh.boardproject.jwt.JwtSecurityConfig
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity // 웹 Security 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) //
@RequiredArgsConstructor
@Configuration
open class SecurityConfig (
         var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
         var jwtAccessDeniedHandler: JwtAccessDeniedHandler,
         var jwtSecurityConfig: JwtSecurityConfig
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder { //? TODO
        return BCryptPasswordEncoder()
    }

    override fun configure(web: WebSecurity) {
        //메서드를 재정의하여 로그인 상관 없이 허용을 해줘야할 리소스 위치를 정의한다.

        web
                .ignoring()
                .antMatchers(
                        //h2-console, favicon, swagger 관련된 요청은 spring security로직을 수행하지 않도록 허용되어야 하는 경로들.
                        "/h2-console/**", "/favicon.ico", "/swagger-ui.html"
                )
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) { // configure 메소드 오버라이드함.
        //메소드를 재정의하여 로그인 URL, 권한분리, logout URL 등등을 설정할 수 있다.

        http /*
                * rest api에서 client는 권한이 필요한 요청을 하기 위해서는
                * 요청에 필요한 인증 정보를(OAuth2, jwt토큰 등)을 포함시켜야 한다.
                * 따라서 서버에 인증정보를 저장하지 않기 때문에 굳이 불필요한 csrf 코드들을 작성할 필요가 없다.
                * */
                .csrf().disable() /*
                * 에러 핸들링 및 예외처리
                * 각 예외처리를 상속받아 재정의한 결과로 핸들링한다.
                * */
                .exceptionHandling() // 예외처리 기능이 작동
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 예외처리 ex) 인증 실패 시 401에러 반환
                //authenticationEntryPoint : 인증 처리 과정에서 예외가 발생한 경우(AuthenticationException(인증되지 않은 요청)인 경우) 예외를 핸들링하는 인터페이스
                .accessDeniedHandler(jwtAccessDeniedHandler) // 인가예외처리 ex) 권한없이 접근 시 403에러 반환
                //accessDeniedHandler : 인증은 성공하였지만, 해당 자원에 접근할 권한이 없는 경우 예외
                .and() /*
                * WebSecurityConfigurerAdapter에서
                * 공격 방지를 위한 기본적인 보안헤더를 삽입해준다.
                * 동일 도메인에서 iframe 접근이 가능하도가록 설정
                * */
                .headers()
                .frameOptions()
                .sameOrigin() /*
                * 세션 설정
                * 스프링시큐리티가 세션을 생성하지도 않고 기존 것을 사용하지도 않음
                * */
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미.
                .antMatchers("/api/auth/authenticate").permitAll() // 권한상관없이 접근하도록 허용한다.
                .antMatchers("/api/auth/signup").permitAll() // 권한상관없이 접근하도록 허용한다.
                .antMatchers(*SWAGGER_PERMIT_URL_ARRAY).permitAll()
                .anyRequest().authenticated() // 나머지 요청들은 모두 인증되어야 한다.
                .and()
                .apply(jwtSecurityConfig)
    }

    companion object {
        private val SWAGGER_PERMIT_URL_ARRAY = arrayOf( /* swagger v2 */
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",  /* swagger v3 */
                "/v3/api-docs/**",
                "/swagger-ui/**"
        )
    }
}