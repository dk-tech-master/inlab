package kr.inlab.www.config;

import kr.inlab.www.security.filter.AuthenticationFilter;
import kr.inlab.www.security.filter.AuthorizationHeaderFilter;
import kr.inlab.www.security.handler.CustomAccessDeniedHandler;
import kr.inlab.www.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final Environment environment;

    // todo [Authorization]1-1. 권한 작업을 configure(HttpSecurity http) 오버라이
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/verification_code","/api/verification_code/check","/", "/login", "/docs/index.html", "/swagger-ui/index.html", "/swagger/**", "/swagger-resources/**", "/v2/api-docs").permitAll(); // 로그인과 메인화면과 회원가입 페이지는 누구나 접근 가능하게 설정
        http.authorizeRequests().antMatchers("/api/users/*/role").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/api/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/api/user-question-history").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users").permitAll();
        http.authorizeRequests().antMatchers("/api/users/*").permitAll();
        http.addFilterBefore(getAuthorizationHeaderFilter(), AuthenticationFilter.class);
        http.addFilter(getAuthenticationFilter());
        http.exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler());
    }

    // todo [Login]1-3. 사용자 인증 필터를 생성하고 AuthenticationManager 가 set 되게 하고 반환
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,environment);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/login");

        return authenticationFilter;
    }

    private AuthorizationHeaderFilter getAuthorizationHeaderFilter() throws Exception {
        return new AuthorizationHeaderFilter();
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    // 인증에 관한 설정을 하는 메서드다.
    // 인증은 다음 단계로 구성된다.
    // 1. select * from user where email =?
    // 2. db_pwd(encrypted) == encrypt(input_pwd)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}