package kr.inlab.www.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

@Slf4j
public class JwtTokenProvider {

    public static final String KEY = "dktechmasterinlabsecurityproject2023";
    public static final String REFRESH = "Refresh";
    public static final String USERNAME = "username";
    public static final String ROLES = "roles";
    public static final String EMAIL = "email";
    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;
    public static final SecretKey key = Keys.hmacShaKeyFor(KEY.getBytes(StandardCharsets.UTF_8));

    public String getJwtFromRequest(HttpServletRequest request) {
        // 1. header에 Authorization 확인
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        // 헤더에 토큰이 없으면 null
        return null;
    }

    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH);
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }

    public String getEmailTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(EMAIL);
    }

    // token으로 사용자 id 조회
    public String getUsernameFromToken(String jwt) {
        final Claims claims = getAllClaimsFromToken(jwt);
        return claims.get(USERNAME, String.class);
    }


    public List<String> getRolesFromToken(String jwt) {
        final Claims claims = getAllClaimsFromToken(jwt);
        List<String> list = claims.get(ROLES, List.class);
        return list;
    }

    public String getEmailFromToken(String emailTokenFromRequest) {
        validateToken(emailTokenFromRequest);
        final Claims claims = getAllClaimsFromToken(emailTokenFromRequest);
        return claims.get(EMAIL, String.class);
    }

    // token으로 사용자 속성정보 조회
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 모든 token에 대한 사용자 속성정보 조회
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    // 토큰 만료 여부 체크
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // 토큰 만료일자 조회
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // JWT accessToken 생성
    public String generateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 2400))// 1시간
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .signWith(key, SignatureAlgorithm.HS256)  // 사  용할 암호화 알고리즘과 secret 값 세팅
            .compact();
    }

    // JWT accessToken 생성
    public String generateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 2400)) // 5시간
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .signWith(key, SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과 secret 값 세팅
            .compact();
    }

    // JWT accessToken 생성
    public Map<String, String> generateEmailToken(Map<String, Object> claims) {
        Map<String, String> tokens = new HashMap<String, String>();
        String emailToken = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 2400)) // 24시간
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .signWith(key, SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과 secret 값 세팅
            .compact();
        tokens.put(EMAIL, emailToken);
        return tokens;
    }

    // JWT accessToken, refreshToken 생성
    public Map<String, String> generateTokenSet(Map<String, Object> claims) {
        Map<String, String> tokens = new HashMap<String, String>();

        String accessToken = generateAccessToken(claims);
        String refreshToken = generateRefreshToken(claims);

        tokens.put(HttpHeaders.AUTHORIZATION, accessToken);
        tokens.put(REFRESH, refreshToken);
        return tokens;
    }

    // 토큰 검증
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public void putPrincipalToClaims(Claims claims, Authentication authResult) {
        claims.put(USERNAME, authResult.getName());// 사용자 이름(username)을 JWT에 추가
    }

    public void putUsernameToClaims(Claims claims, String username) {
        claims.put(USERNAME, username);
    }

    public void putRolesToClaims(Claims claims, List<String> roles) {
        claims.put(ROLES, roles);
    }

    public void putRolesToClaims(Claims claims, Authentication authResult) {
        claims.put(ROLES, authResult.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList())); // 사용자 역할(role)을 JWT에 추가
    }

    public void putEmailToClaims(Claims claims, String email) {
        claims.put(EMAIL, email);
    }

}
