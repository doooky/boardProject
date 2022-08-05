package kdh.boardproject.jwt

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Slf4j
@Component
@RequiredArgsConstructor
class JwtFilter( // JWT를 위한 커스텀 필터
        private var tokenProvider: TokenProvider
): GenericFilterBean() {// GenericFilterBean : 스프링 컨테이너에 속해있는 Bean정보를 가져올 수 있게 함.

    val log = KotlinLogging.logger {}

    // 실제 필터링 로직은 doFilter 내부에 작성
    // jwt 토큰의 인증 정보를 SecurityContext에 저장하는 역할
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val httpServletRequest = servletRequest as HttpServletRequest
        val jwt = resolveToken(httpServletRequest) //request에서 토큰을 받아서
        val requestURI = httpServletRequest.requestURI
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { //validateToken 토큰의 유효성 검사 메소드
            // request토큰을 받아서 유효성 검증으로 하고 정상 토큰이면
            val authentication = tokenProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication //SecurityContext에 저장한ㄷㅏ.
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication!!.name, requestURI)
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI)
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    // request header에서 토큰 정보를 꺼내오는 메소드
    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}