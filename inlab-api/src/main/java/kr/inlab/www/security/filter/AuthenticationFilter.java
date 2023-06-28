package kr.inlab.www.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.inlab.www.dto.request.LoginRequestDto;
import kr.inlab.www.security.jwt.JwtTokenProvider;
import kr.inlab.www.security.service.AuthenticationProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // todo [Login]1-2. 사용자 인증 처리를 위한 클래스 추가
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    /**
     * 사용자가 로그인을 하면 가장 먼저 실행되는 메서드 UsernamePasswordAuthenticationFilter는 사용자의 아이디와 비밀번호를 받아 Spring Security의 인증 프로세스를
     * 시작하고, 인증에 필요한 정보를 AuthenticationManager에 전달하는 역할을 합니다.
     */
    /**
     * 사용자가 입력한 email과 password가 넘어오면 new UsernamePasswordAuthenticationToken( creds.getUsername(), creds.getPassword(),
     * new ArrayList<>())로 로 토큰을 만들고 만들어진 토큰인  token 은 getAuthenticationManager().authenticate(token) 형식으로 인증에 사용된다.
     * authenticationManager 가 인증을 완료하면 {@link AuthenticationProviderService} 의 return new
     * UsernamePasswordAuthenticationToken(user,password,user.getAuthorities()); 가 반환되고 UserDetails 타입의 user 를 Principal
     * 매개변수(new UsernamePasswordAuthenticationToken 의 첫번째 인자)에 넣어줬다.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        try {
            // 사용자의 로그인 정보가 넘어오는데 자동으로 변환이 안된다. 그래서 ObjectMapper 를 사용하여 LoginRequestDto 로 변환해준다.
            //think 아니 controller 에서는 @RequestBody 붙히면 알아서 매핑해줬는데 이거는 왜 이렇게 불편하게 변환함?
            LoginRequestDto creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
            // Spring Security가 제공하는 인증 서비스와의 연동을 원활하게 하기 위해 LoginRequest를 UsernamePassword 토큰으로 변환해준다.
            // 토큰으로 변환해주었으니 이제 그 토큰을 처리하기 위한 AuthenticationManager 에 인증 작업을 요청해야한다.
            return this.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    new ArrayList<>()
                )
                // authenticate 메서드로 인증에 성공하면 반환되는 객체는 Authentication 이고 아래의 successfulAuthentication 가 실행된다.
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그인이 성공적으로 끝나고 토큰을 반환하기 위한 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
        Claims claims = Jwts.claims();

        jwtTokenProvider.putPrincipalToClaims(claims, authResult);
        jwtTokenProvider.putRolesToClaims(claims, authResult);

        Map<String, String> stringStringMap = jwtTokenProvider.generateTokenSet(claims);

        stringStringMap.forEach(response::addHeader);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
        response.addHeader("error", "error");
    }
}