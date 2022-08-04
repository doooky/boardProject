package kdh.boardproject.service

import kdh.boardproject.dto.user.UserDto
import kdh.boardproject.entity.Authority
import kdh.boardproject.entity.User
import kdh.boardproject.exception.DuplicateMemberException
import kdh.boardproject.repository.UserRepository
import kdh.boardproject.util.SecurityUtil
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@RequiredArgsConstructor
class UserService {
    private val userRepository: UserRepository? = null
    private val passwordEncoder: PasswordEncoder? = null
    @Transactional
    fun signup(userDto: UserDto): User {
        if (userRepository!!.findOneWithAuthoritiesById(userDto.getUsername())!!.orElse(null) != null) {
            throw DuplicateMemberException("이미 가입되어 있는 유저입니다.")
        }
        val authority: Authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build()
        val user: User = User.builder()
                .id(userDto.getUsername())
                .pw(passwordEncoder!!.encode(userDto.getPassword()))
                .name(userDto.getNickname())
                .authorities(setOf(authority))
                .activated(true)
                .build()
        return userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun getUserWithAuthorities(id: String?): Optional<User?>? {
        return userRepository!!.findOneWithAuthoritiesById(id)
    }

    @get:Transactional(readOnly = true)
    val myUserWithAuthorities: Optional<User?>
        get() = SecurityUtil.getCurrentUsername().flatMap { id: String? -> userRepository!!.findOneWithAuthoritiesById(id) }
}