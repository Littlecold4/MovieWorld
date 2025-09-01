package com.example.movieworld.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.movieworld.user.service.UserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component // 스프링 빈으로 등록
@Slf4j // 로깅 사용
@RequiredArgsConstructor // final 필드들을 위한 생성자 자동 주입 (Lombok)
public class TokenProvider {

    @Value("${jwt.secretkey}") // application.properties/yml에서 secretkey 주입
    private String JWT_SECRET; // JWT_SECRET 변수명 명확화
    private static final int DAY = 24 * 60 * 60;
    // JWT 토큰의 유효기간: 3일 (단위: milliseconds)
    private static final int JWT_TOKEN_VALID_MILLI_SEC = 3 * DAY * 1000;
    public static final String CLAIM_USERID = "USER_ID";
    //    public static final String CLAIM_OAUTH = "OAUTH_INFO";
    public static final String ISSUER = "MovieWorld";

    private final UserDetailsService userDetailsService; // 사용자 정보 로드를 위한 서비스
    private static final String TOKEN_PREFIX = "Bearer "; // 토큰 프리픽스
    private final long validityInMilliseconds = 3600000;
    /**
     * HTTP Request 헤더에서 'Bearer' 토큰을 추출합니다.
     * @param request HTTP 요청 객체
     * @return 추출된 토큰 문자열 (Bearer 제거) 또는 null (토큰 없을 시)
     */
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        log.info("요청 Authorization 헤더 값: {}", bearerToken); // 로그는 유지합니다.

        if(bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * 토큰에서 사용자 정보(username)를 추출하여 인증 객체(Authentication)를 생성합니다.
     * 이 메서드에서는 HTTP 응답을 조작하지 않습니다.
     *
     * @param token JWT 토큰
     * @return 생성된 Authentication 객체
     * @throws JWTVerificationException 토큰 검증 실패 시 (만료, 유효하지 않은 서명 등)
     */
    public Authentication getAuthentication(String token) {
        // 1. 토큰 유효성 검증 및 디코딩 (여기서 JWT 관련 예외가 발생할 수 있습니다.)
        DecodedJWT decodedJWT = isValidToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않거나 검증에 실패한 토큰입니다.")); // 이 예외는 JwtAuthenticationFilter에서 잡을 것
        // 2. 토큰 만료 시간 검사
        Date now = new Date();
        if (decodedJWT.getExpiresAt() == null || decodedJWT.getExpiresAt().before(now)) {
            throw new TokenExpiredException("만료된 엑세스 토큰입니다."); // 만료 시 TokenExpiredException 발생
        }

        // 3. 토큰에서 사용자 ID(Subject) 추출 (일반적으로 subject에 사용자 ID/이름을 저장합니다.)
        String username = decodedJWT.getSubject();
        if (username == null) {
            throw new IllegalArgumentException("토큰에 사용자 정보(Subject)가 없습니다.");
        }

        // 4. 추출된 사용자 ID로 UserDetails 로드
        UserDetails userDetails = userDetailsService.loadUserByUserName(username); // userDetailsService의 메서드명은 loadUserByUsername입니다.

        // 5. Authentication 객체 생성 및 반환
        return new UsernamePasswordAuthenticationToken(
                userDetails, // principal (사용자 정보)
                null, // credentials (패스워드. 여기서는 JWT 인증이므로 null 처리)
                userDetails.getAuthorities() // 권한 정보
        );
    }

    /**
     * JWT 토큰의 유효성을 검증합니다.
     * 유효하지 않으면 JWTVerificationException (또는 하위 예외)을 던집니다.
     * @param token 검증할 JWT 토큰
     * @return 검증된 DecodedJWT 객체 (Optional로 감쌈)
     * @throws JWTVerificationException 토큰 검증 실패 시 발생
     */
    public Optional<DecodedJWT> isValidToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET.getBytes()))
                    .build();
            DecodedJWT jwt = verifier.verify(token); // 토큰 검증 시도
            return Optional.of(jwt); // 유효하면 Optional로 감싸 반환
        } catch (JWTVerificationException e) { // 토큰 만료 포함, 모든 JWT 검증 관련 예외 처리
            log.error("JWT 토큰 검증 실패: {}", e.getMessage());
            throw e; // 필터에서 이 예외를 catch 하여 적절히 처리할 수 있도록 다시 던짐
        }
    }

    public String generateJwtToken(String userEmail) {
        String token = null;


        token =JWT.create()
                .withSubject(userEmail)
                .withIssuedAt(new Date()) // 토큰 발급 시간
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC)) // 토큰 만료 시간
                // .withClaim("customClaim", "value") // 필요한 경우 다른 클레임 추가
                .sign(generateAlgorithm(JWT_SECRET)); // 서명에 사용할 알고리즘과 시크릿 키

        return token;
    }
    private Map<String, Object> createClaims(String userEmail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USERID, userEmail);

        return claims;
    }
    private static Algorithm generateAlgorithm(String secretKey) {
        return Algorithm.HMAC256(secretKey.getBytes());
    }

}