package kr.inlab.www.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.inlab.www.common.exception.LoginBlockedException;
import kr.inlab.www.common.util.CreateHeaders;
import kr.inlab.www.dto.common.ResponseErrorDto;
import kr.inlab.www.dto.request.RequestLoginDto;
import kr.inlab.www.entity.User;
import kr.inlab.www.security.jwt.JwtTokenProvider;
import kr.inlab.www.security.service.AuthenticationProviderService;
import kr.inlab.www.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }

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
            RequestLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), RequestLoginDto.class);
            request.setAttribute("username", creds.getUsername());

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

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
        String email = authResult.getName();
        User user = userService.getUserByEmail(email);
        userService.resetLoginAttempt(email);

        Claims claims = Jwts.claims();

        jwtTokenProvider.putPrincipalToClaims(claims, authResult);
        jwtTokenProvider.putRolesToClaims(claims, authResult);

        Map<String, String> stringStringMap = jwtTokenProvider.generateTokenSet(claims);
        stringStringMap.forEach(response::addHeader);

        checkPasswordChangeRequiredAndThenSetHeader(email, response); // 최근 비밀번호 변경이 필요한지 여부 확인

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseJson = new HashMap<>();

        responseJson.put("userId", user.getUserId().toString());
        responseJson.put("nickname", user.getNickname());
        responseJson.put("role", authResult.getAuthorities().stream().collect(Collectors.toList()).get(0).toString());

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonResponse = objectMapper.writeValueAsString(responseJson);

        response.getWriter().write(jsonResponse);
    }

    /**
     * 비밀번호 변경이 현재 시점으로 3개월 이상됐을 경우 Password-Change-Required 를 헤더에 추가한다.
     */
    private void checkPasswordChangeRequiredAndThenSetHeader(String email, HttpServletResponse response) {
        if (isPasswordChangeRequired(userService.getUserByEmail(email).getPasswordModifiedAt())) {
            response.addHeader(CreateHeaders.PASSWORD_CHANGE_REQUIRED, CreateHeaders.TRUE);
        }
    }

    private boolean isPasswordChangeRequired(LocalDateTime passwordModifiedAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMonthsAgo = now.minus(3, ChronoUnit.MONTHS);

        return passwordModifiedAt.isBefore(threeMonthsAgo);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ResponseErrorDto responseErrorDto = ResponseErrorDto.builder()
            .code("UNAUTHORIZED")
            .message(failed.getMessage())
            .build();
        String jsonResponse = objectMapper.writeValueAsString(responseErrorDto);
        response.getWriter().write(jsonResponse);

        if (failed instanceof LoginBlockedException) {
            response.addHeader(CreateHeaders.LOGIN_FAIL_BLOCK, LocalDateTime.now().plusMinutes(30).toString());
        }
    }
}
