package Webprogramming.KOR_Polimeter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index", "/css/**", "/js/**", "/auth/kakao/callback").permitAll() // 인증 없이 접근 가능
                        .requestMatchers("/login/oauth2/**", "/oauth2/**").permitAll() // OAuth2 경로 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/kakao") // 카카오 로그인 페이지
                        .defaultSuccessUrl("/index", true) // 로그인 성공 후 리디렉션 경로
                        .failureUrl("/login?error=true") // 로그인 실패 시 리디렉션 경로
                )
                .csrf(csrf -> csrf.disable()); // 개발 중 CSRF 비활성화 (필요 시 활성화)

        return http.build();
    }
}





