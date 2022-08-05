package kdh.boardproject.controller

import kdh.boardproject.dto.user.LoginDto
import kdh.boardproject.dto.user.TokenDto
import kdh.boardproject.jwt.TokenProvider
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
class AuthController(
        private val tokenProvider: TokenProvider,
        private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {

    @PostMapping("/authenticate")
    fun authorize(@RequestBody loginDto: @Valid LoginDto): ResponseEntity<TokenDto> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password)
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)
        return ResponseEntity.ok(TokenDto(jwt))
    }
}