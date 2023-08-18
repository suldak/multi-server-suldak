package com.sulsul.suldaksuldak.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll()
//                .antMatchers("/**").authenticated() // 인가된 사용자만 접근 가능하도록 설정
//			  .antMatchers("게시물등").hasRole(Role.USER.name()) // 특정 ROLE을 가진 사용자만 접근 가능하도록 설정
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
        ;

        return http.build();
    }
}
