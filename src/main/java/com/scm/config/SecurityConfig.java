package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.services.CustomUserDetailService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailService userDetailService;
    private final OAuthAuthenticationSuccessHandler handler;

    public SecurityConfig(CustomUserDetailService userDetailService, OAuthAuthenticationSuccessHandler handler) {
        this.userDetailService = userDetailService;
        this.handler = handler;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> {
                authorize.requestMatchers("/user/**").authenticated();
                authorize.anyRequest().permitAll();
            })
            .formLogin(formLogin -> {
                formLogin
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .successForwardUrl("/user/dashboard")
                    .failureForwardUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password");
            })
            .csrf(AbstractHttpConfigurer::disable)
            .logout(logout -> {
                logout
                    .logoutUrl("/do-logout")
                    .logoutSuccessUrl("/login?logout=true");
            })
            .oauth2Login(oauth -> {
                oauth
                    .loginPage("/login")
                    .successHandler(handler);
            });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
