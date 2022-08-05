package kdh.boardproject.util

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Slf4j
object SecurityUtil {
    val log = KotlinLogging.logger {}
    val currentUsername: String?
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null) {
                log.debug("Security Context에 인증 정보가 없습니다.")
                return null
            }
            var username: String? = null
            if (authentication.principal is UserDetails) {
                val springSecurityUser = authentication.principal as UserDetails
                username = springSecurityUser.username
            } else if (authentication.principal is String) {
                username = authentication.principal as String
            }
            return username?: username
        }
}