package com.mumu.mumu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/kakao_login_button.png", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login/page") // ✅ 우리가 만든 로그인 페이지
                        .loginProcessingUrl("/login") // ✅ Spring이 로그인 요청을 처리할 경로
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login/page") // 로그아웃 후 다시 이 페이지로
                        .permitAll()
                );

        return http.build();
    }
}