package kdh.boardproject.config;

import lombok.RequiredArgsConstructor;
import kdh.boardproject.jwt.JwtAccessDeniedHandler;
import kdh.boardproject.jwt.JwtAuthenticationEntryPoint;
import kdh.boardproject.jwt.JwtSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // 웹 Security 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtSecurityConfig jwtSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                  "/h2-console/**"
                        ,"/favicon.ico"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*
                * rest api에서 client는 권한이 필요한 요청을 하기 위해서는
                * 요청에 필요한 인증 정보를(OAuth2, jwt토큰 등)을 포함시켜야 한다.
                * 따라서 서버에 인증정보를 저장하지 않기 때문에 굳이 불필요한 csrf 코드들을 작성할 필요가 없다.
                * */
                .csrf().disable()

                /*
                * 에러 핸들링 및 예외처리
                * 각 예외처리를 상속받아 재정의한 결과로 핸들링한다.
                * */
                .exceptionHandling() // 예외처리 기능이 작동
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 예외처리 ex) 인증 실패 시 401에러 반환
                .accessDeniedHandler(jwtAccessDeniedHandler) // 인가예외처리 ex) 권한없이 접근 시 403에러 반환

                .and()
                /*
                * WebSecurityConfigurerAdapter에서
                * 공격 방지를 위한 기본적인 보안헤더를 삽입해준다.
                * 동일 도메인에서 iframe 접근이 가능하도록 설정
                * */
                .headers()
                .frameOptions()
                .sameOrigin()

                /*
                * 세션 설정
                * 스프링시큐리티가 세션을 생성하지도 않고 기존 것을 사용하지도 않음
                * */
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미.
                .antMatchers("/api/hello").permitAll() // 권한상관없이 접근하도록 허용한다.
                .antMatchers("/api/authenticate").permitAll() // 권한상관없이 접근하도록 허용한다.
                .antMatchers("/api/signup").permitAll() // 권한상관없이 접근하도록 허용한다.
                .anyRequest().authenticated() // 나머지 요청들은 모두 인증되어야 한다.

                .and()
                .apply(jwtSecurityConfig);

    }
}
