package Webprogramming.KOR_Polimeter;

import Webprogramming.KOR_Polimeter.controller.CustomLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public LogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index", "/css/**", "/js/**", "/auth/kakao/callback").permitAll() // 인증 없이 접근 가능
                        .requestMatchers("/login/oauth2/**", "/oauth2/**").permitAll() // OAuth2 경로 허용
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers("/resources/**").permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/votes", "/result").permitAll()
                        .requestMatchers("/default", "/main", "/searching", "/vote", "/vote_result", "/details").permitAll()
                        .requestMatchers("/details/**").permitAll()
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/kakao") // 카카오 로그인 페이지
                        .defaultSuccessUrl("/index", true) // 로그인 성공 후 리디렉션 경로
                        .failureUrl("/login?error=true") // 로그인 실패 시 리디렉션 경로
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 요청 경로
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 리디렉션 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .clearAuthentication(true) // 인증 정보 초기화
                        .addLogoutHandler(customLogoutHandler())
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll() // 로그아웃 요청 허용
                )
                .csrf(csrf -> csrf.disable()); // 개발 중 CSRF 비활성화 (필요 시 활성화)

        return http.build();
    }
}