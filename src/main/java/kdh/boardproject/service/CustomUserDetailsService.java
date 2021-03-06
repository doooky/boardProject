package kdh.boardproject.service;

import kdh.boardproject.entity.User;
import kdh.boardproject.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
* @Component("빈 이름")
* 빈의 이름을 따로 지정할 수 있다.
* 그러나 한 패키지 내에서 여러개의 이름으로 빈을 등록할 수는 없다.
* 아래 클래스는 userDetailsService라는 이름의 빈으로 등록이 된다.
* */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String id) {
        return userRepository.findOneWithAuthoritiesById(id)
                .map(user -> createUser(id, user))
                .orElseThrow(() -> new UsernameNotFoundException(id + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String id, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(id + " -> 활성화되어 있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getId(),
                user.getPw(),
                grantedAuthorities);
    }
}