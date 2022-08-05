package kdh.boardproject.service

import kdh.boardproject.exception.CustomException
import kdh.boardproject.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/*
* @Component("빈 이름")
* 빈의 이름을 따로 지정할 수 있다.
* 그러나 한 패키지 내에서 여러개의 이름으로 빈을 등록할 수는 없다.
* 아래 클래스는 userDetailsService라는 이름의 빈으로 등록이 된다.
* */
@Component("userDetailsService")
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    @Transactional
    override fun loadUserByUsername(id: String): UserDetails {
        var user = userRepository.findOneWithAuthoritiesById(id)
                ?: throw UsernameNotFoundException("$id -> 데이터베이스에서 찾을 수 없습니다.")
        return createUser(id = id, user = user)
//                .map{ user: kdh.boardproject.entity.User -> createUser(id, user) }
//                .orElseThrow { UsernameNotFoundException("$id -> 데이터베이스에서 찾을 수 없습니다.") }
    }

    private fun createUser(id: String, user: kdh.boardproject.entity.User): User {

            if (!user.activated) { //isActivated
                throw RuntimeException("$id -> 활성화되어 있지 않습니다.")
            }

        val grantedAuthorities : List<GrantedAuthority>? =
                user.authorities?.map { authority -> SimpleGrantedAuthority(authority.authorityName) }

        return User(user.id,
                user.pw,
                grantedAuthorities  )
    }
}