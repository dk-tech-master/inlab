package kr.inlab.www.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.inlab.www.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationHeaderFilter extends OncePerRequestFilter {

    public JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        // 현재 요청에서 Refresh 토큰을 가져온다.
        final String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
        String jwt = null;
        // refreshToken 이 있는 경우(accessToken 만료로 인한 재요청)
        if (refreshToken != null) {
            // refreshToken 이 만료되거나 유효하지 않은 경우(재 로그인)
            if (jwtTokenProvider.isTokenExpired(refreshToken) && jwtTokenProvider.validateToken(refreshToken)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // todo front 에게 알려주기
                response.setHeader("Refresh-Token-Expired", "true");
                // todo 여기서 return 하면 사용자에게 바로 응답이 가나?
                return;
            }
            // refreshToken 이 만료되지 않은 경우 access 토큰을 재발급하고
            jwt = jwtTokenProvider.getJwtFromRequest(request);
            if (Objects.isNull(jwt)) {
                // accessToken 이 유효x(재 로그인)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // todo front 에게 알려주기
                response.setHeader("Access-Token-InValid", "true");
                return;
            }
            jwt = reIssueAccessToken(refreshToken, response);
        } else {
            // refresh 토큰이 없는 경우(일반적인 서비스요청)
            // header 에서 access 토큰을 받아오도록 함
            jwt = jwtTokenProvider.getJwtFromRequest(request);
            // access 토큰이 유효o
            if (jwtTokenProvider.validateToken(jwt)) {
                // access 토큰이 유효o 만료o(refresh, access 포함한 재요청을 요청)
                if (jwtTokenProvider.isTokenExpired(jwt)) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    // todo front 에게 알려주기
                    response.setHeader("Access-Token-Expired", "true");
                    return;
                }
            } else {
                // accessToken 이 유효x(재 로그인)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                // todo front 에게 알려주기
                response.setHeader("Access-Token-InValid", "true");
                return;
            }
        }

        String username = jwtTokenProvider.getUsernameFromToken(jwt);
        List<String> roles = jwtTokenProvider.getRolesFromToken(jwt);

        List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        Authentication authentication =
            new UsernamePasswordAuthenticationToken(
                username,
                "",
                authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String reIssueAccessToken(String refreshToken, HttpServletResponse response) {
        Claims claims = Jwts.claims();
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        List<String> roles = jwtTokenProvider.getRolesFromToken(refreshToken);

        jwtTokenProvider.putUsernameToClaims(claims, username);
        jwtTokenProvider.putRolesToClaims(claims, roles);

        String accessToken = jwtTokenProvider.generateAccessToken(claims);
        response.addHeader(HttpHeaders.AUTHORIZATION, accessToken);
        return accessToken;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return
            request.getServletPath().equals("/api/users/health_check")
                || request.getServletPath().equals("/api/users")
                || request.getServletPath().equals("/login")
                || request.getServletPath().equals("/docs/index.html")
                || request.getServletPath().equals("/api/questions");
    }
}
