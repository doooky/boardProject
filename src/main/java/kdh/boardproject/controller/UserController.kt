package kdh.boardproject.controller

import kdh.boardproject.dto.user.UserDto
import kdh.boardproject.entity.User
import kdh.boardproject.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
class UserController {
    private val userService: UserService? = null
    @PostMapping("/signup")
    fun signup(
            @RequestBody userDto: @Valid UserDto?
    ): ResponseEntity<User> {
        return ResponseEntity.ok(userService!!.signup(userDto!!))
    }

    @get:PreAuthorize("hasAnyRole('USER','ADMIN')")
    @get:GetMapping("/")
    val myUserInfo: ResponseEntity<User>
        get() = ResponseEntity.ok(userService!!.myUserWithAuthorities.get())

    @GetMapping("{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getUserInfo(@PathVariable username: String?): ResponseEntity<User> {
        return ResponseEntity.ok(userService!!.getUserWithAuthorities(username)!!.get())
    }
}