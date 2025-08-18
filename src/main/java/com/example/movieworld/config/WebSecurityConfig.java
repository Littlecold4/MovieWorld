package com.example.movieworld.config;

import com.example.movieworld.jwt.JwtAuthenticationFilter;
import com.example.movieworld.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration; // 이 import 추가
import org.springframework.web.cors.CorsConfigurationSource; // 이 import 추가
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // 이 import 추가
import java.util.Arrays; // 이 import 추가

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 최신 스프링 시큐리티에서는 @EnableMethodSecurity 로 대체되기도 합니다.
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;

    // 생성자 문법 수정!
    public WebSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호를 BCrypt 해시 알고리즘으로 인코딩해주는 빈을 등록합니다.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // 1. 세션 관리 (Session Management)
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() 대신
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 2. 요청 캐시 비활성화 (Request Cache)
                // .requestCache().disable() 대신
                .requestCache(requestCache -> requestCache.disable())
                // 3. 폼 로그인 비활성화 (Form Login)
                // .formLogin().disable() 대신
                .formLogin(formLogin -> formLogin.disable())
                // 4. HTTP 기본 인증 비활성화 (HTTP Basic)
                // .httpBasic().disable() 대신
                .httpBasic(httpBasic -> httpBasic.disable())
                // 5. CORS 설정 (CORS)
                // .cors().configurationSource(corsConfigurationSource()).and() 대신
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 6. CSRF 보호 비활성화 (CSRF)
                // .csrf().disable() 대신
                .csrf(csrf -> csrf.disable())
                // 7. H2-Console 등을 위한 프레임 옵션 비활성화 (Headers - Frame Options)
                // .headers().frameOptions().disable() 대신
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        ;

        http
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers(HttpMethod.GET, "/get/korean").permitAll()
                                .requestMatchers(HttpMethod.GET, "/get/genre").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/signup").permitAll()
                                .anyRequest().authenticated()
                );
        return http.build();
    }

    // CorsConfigurationSource 빈 정의 추가!
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // 모든 출처 허용, 특정 도메인으로 제한하는 것이 보안에 더 좋음
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
