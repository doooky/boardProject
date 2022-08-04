package kdh.boardproject.controller

import kdh.boardproject.dto.user.LoginDtoimport

kdh.boardproject.dto.user.TokenDtoimport kdh.boardproject.jwt.TokenProviderimport lombok.RequiredArgsConstructorimport org.springframework.http.ResponseEntityimport org.springframework.security.authentication.UsernamePasswordAuthenticationTokenimport org.springframework.security.config.annotation .authentication.builders.AuthenticationManagerBuilderimport org.springframework.security.core.context.SecurityContextHolderimport org.springframework.web.bind.annotation .*import javax.validation.Valid

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
class AuthController {
    private val tokenProvider: TokenProvider? = null
    private val authenticationManagerBuilder: AuthenticationManagerBuilder? = null
    @PostMapping("/authenticate")
    fun authorize(@RequestBody loginDto: @Valid LoginDto?): ResponseEntity<TokenDto> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        val authentication = authenticationManagerBuilder!!.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider!!.createToken(authentication)
        return ResponseEntity.ok(TokenDto(jwt))
    }
}