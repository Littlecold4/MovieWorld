package com.example.movieworld.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException; // JWT 검증 관련 예외
import com.auth0.jwt.exceptions.TokenExpiredException; // 토큰 만료 예외
import com.fasterxml.jackson.databind.ObjectMapper; // JSON 응답을 위해
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component; // 필터가 스프링 빈으로 등록될 경우
import org.springframework.web.filter.OncePerRequestFilter; // 요청당 한 번만 실행되는 필터

import java.io.IOException;

@Component // 스프링 빈으로 등록
@Slf4j // 로깅 사용
@RequiredArgsConstructor // final 필드들을 위한 생성자 자동 주입 (Lombok)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    // permitAll()로 설정된 경로들을 명시 (여기서는 임시로 하드코딩, 실제는 유연하게 관리 필요)
    // "/api/v1/user/signup" 과 같은 회원가입 경로는 permitAll()이어야 합니다.
    private static final String[] PERMIT_ALL_PATHS = {
            "/user/signup",
            "/get/korean",
            "/h2-console/**",
            "/swagger-ui/**",
            "/get/genre",
            "/user/login"
            // 여기에 WebSecurityConfig에서 permitAll()로 설정한 모든 경로를 추가해주세요.
            // 와일드카드 문자(예: /**) 처리 로직도 필요합니다.
    };

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); // 현재 요청 URI 가져오기

        // 1. 요청 URI가 permitAll() 경로에 해당하는지 확인
        // 이 부분은 AntPathMatcher나 PatternMatchUtils.simpleMatch 같은 유틸리티를 사용하면 더 강력하게 처리할 수 있습니다.
        boolean isPermitAllPath = false;
        for (String path : PERMIT_ALL_PATHS) {
            // 경로 매칭 규칙 (예: "/h2-console/**" 처리)
            if (path.endsWith("/**")) { // ** 와일드카드 처리
                String base = path.substring(0, path.length() - 3); // "/**" 제거
                if (requestURI.startsWith(base)) {
                    isPermitAllPath = true;
                    break;
                }
            } else if (requestURI.equals(path)) { // 정확한 경로 일치
                isPermitAllPath = true;
                break;
            }
        }

        // 2. permitAll() 경로라면 토큰 검증 없이 다음 필터 체인으로 넘김
        // SecurityContextHolder는 비워져있는 상태로, 인가(Authorization) 단계에서 permitAll() 규칙에 의해 허용될 것
        if (isPermitAllPath) {
            log.info("PermitAll 경로({}), 토큰 검증 스킵하고 다음 필터로 진행합니다.", requestURI);
            filterChain.doFilter(request, response);
            return; // 중요한 부분: 토큰 검증 로직을 건너뛰고 바로 반환
        }

        // 3. permitAll() 경로가 아니라면 토큰 유무 확인 및 검증 로직 수행
        String token = tokenProvider.resolveToken(request); // HTTP Request에서 토큰 추출

        if (token == null) { // 토큰이 아예 없는 경우 (하지만 permitAll() 경로가 아님)
            // 토큰이 필수인 경로에 토큰 없이 접근했으므로 401 UNAUTHORIZED 응답
            log.warn("인증 토큰이 없습니다. URI: {}", requestURI);
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "인증 토큰이 필요합니다.");
            return; // 요청 처리 중단
        }

        // 4. 토큰이 있는 경우, 유효성 검증 및 인증 객체 설정
        try {
            Authentication authentication = tokenProvider.getAuthentication(token); // 토큰 검증 및 인증 객체 생성
            SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 인증 정보 저장
            log.info("인증 성공! 사용자: {}", authentication.getName());

        } catch (TokenExpiredException e) { // 토큰 만료 시
            log.error("만료된 JWT 토큰입니다. URI: {}", requestURI, e);
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.");
            return; // 요청 처리 중단
        } catch (JWTVerificationException | IllegalArgumentException e) { // 토큰 유효성 검증 실패 (잘못된 서명, 형식 등)
            log.error("유효하지 않은 JWT 토큰입니다. URI: {}", requestURI, e);
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return; // 요청 처리 중단
        } catch (Exception e) { // 그 외 예상치 못한 예외
            log.error("인증 처리 중 알 수 없는 오류 발생. URI: {}", requestURI, e);
            sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "인증 처리 중 오류가 발생했습니다.");
            return; // 요청 처리 중단
        }

        // 모든 처리 완료 후, 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * 클라이언트에게 오류 응답을 보냅니다.
     * @param response HttpServletResponse 객체
     * @param status HTTP 상태 코드
     * @param message 클라이언트에게 보낼 오류 메시지
     * @throws IOException 응답 작성 중 오류 발생 시
     */
    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // ObjectMapper를 사용하여 JSON 형태로 메시지 전송
        new ObjectMapper().writeValue(response.getWriter(), "{\"error\": \"" + message + "\"}");
    }
}