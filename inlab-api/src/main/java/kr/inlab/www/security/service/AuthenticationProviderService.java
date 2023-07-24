package kr.inlab.www.security.service;

import java.util.Objects;
import javax.transaction.Transactional;
import kr.inlab.www.entity.User;
import kr.inlab.www.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        // todo 8: 아이디를 통해 찾아온 userDetails 형태의 객체를 반환받고
        UserDetails user = userService.loadUserByUsername(username);

        // todo 9: userDetails 형태의 객체의 비밀번호(DB 에서 조회한 값)와 로그인할 때 입력한 비밀번호를 비교한다.
        if (passwordEncoder.matches(password, user.getPassword())) {
            // todo 10: 만약 동일하면 알맞은 로그인으로 보고 인증 토큰을 만든뒤 반환한다.
            return new UsernamePasswordAuthenticationToken(
                username,
                password,
                user.getAuthorities());
        } else {
            doLoginFailProcessing(user);
            throw new BadCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }
    @Transactional
    public void doLoginFailProcessing(UserDetails user) {
        User loginFailUser = userService.getUserByEmail(user.getUsername());
        int maxAttempts = Integer.parseInt(Objects.requireNonNull(environment.getProperty("myapp.max-attempt")));
        int currentAttempts = loginFailUser.getLoginAttempt();
        int remainingAttempts = maxAttempts - currentAttempts;
        if (remainingAttempts <= 0) {
            userService.updateUserBlockUntil(loginFailUser.getEmail());
        } else {
            userService.increaseLoginAttempt(loginFailUser.getEmail());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
