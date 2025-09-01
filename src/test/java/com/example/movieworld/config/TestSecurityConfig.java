// TestSecurityConfig.java (테스트 전용)
package com.example.movieworld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers; // 필요시

// Mocking을 위한 빈만 설정
@TestConfiguration // 테스트 전용 설정을 나타내는 어노테이션
@EnableWebSecurity
public class TestSecurityConfig {

    // 테스트를 위해 Minimalistic SecurityFilterChain을 정의합니다.
    // 실제 JWT 필터 등을 적용하지 않고, 필요한 경우 목킹된 필터를 addFilterBefore로 추가합니다.
    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll() // 모든 요청 허용 (컨트롤러 매핑 확인용)
                        // .anyRequest().authenticated() // 인증이 필요한 경우
                );
        return http.build();
    }

    // 추가적으로 필요한 경우 (예: 인증 관련 목킹 빈) 여기에 정의할 수 있습니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}