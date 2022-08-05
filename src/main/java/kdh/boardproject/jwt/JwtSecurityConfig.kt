package kdh.boardproject.jwt

import lombok.RequiredArgsConstructor
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@RequiredArgsConstructor
@Component
class JwtSecurityConfig ( //TokenProvider, JWtFilter를 SecurityConfig에 적용할 때 사용
        private val jwtFilter: JwtFilter
        ): SecurityConfigurerAdapter<DefaultSecurityFilterChain?, HttpSecurity>() { //?검색해도 제대로 된 설명이 나오지 않음 TODO

    override fun configure(http: HttpSecurity) {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}