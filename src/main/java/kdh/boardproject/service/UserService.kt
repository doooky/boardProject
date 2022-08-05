package kdh.boardproject.service

import kdh.boardproject.dto.user.UserDto
import kdh.boardproject.entity.Authority
import kdh.boardproject.entity.User
import kdh.boardproject.repository.UserRepository
import kdh.boardproject.util.SecurityUtil
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.function.Function

@Service
@RequiredArgsConstructor
class UserService (
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder

        ){

    @Transactional
    fun signup(userDto: UserDto):User {
//        if (userRepository!!.findOneWithAuthoritiesById(userDto.username)?: (null) != null) {
//            throw DuplicateMemberException("이미 가입되어 있는 유저입니다.")}
//        }
        val authority: Authority =
                Authority(authorityName = "ROLE_USER")
//                Authority.builder()
//
//                .authorityName("ROLE_USER")
//                .build()
        val user: User =
                User(
                        id = userDto.username,
                        pw = passwordEncoder!!.encode(userDto.password),
                        name = userDto.nickname,
                        authorities = setOf(authority),
                        activated = true,
                        )
//                User.builder()
//                .id(userDto.getUsername())
//                .pw(passwordEncoder!!.encode(userDto.getPassword()))
//                .name(userDto.getNickname())
//                .authorities(setOf(authority))
//                .activated(true)
//                .build()
        userRepository?.save(user)
    return user

    }

    @Transactional(readOnly = true)
    fun getUserWithAuthorities(id: String): User? {
        return userRepository!!.findOneWithAuthoritiesById(id)
    }

    @Transactional(readOnly = true)
    fun getMyUserWithAuthorities(): User? {
//        return currentUsername.flatMap(Function<String, Optional<out U?>?> { id: String? -> userRepository!!.findOneWithAuthoritiesById(id!!) })
        return userRepository!!.findOneWithAuthoritiesById(SecurityUtil.currentUsername!!)
    }


//    { id: String -> userRepository!!.findOneWithAuthoritiesById(id) }
}
