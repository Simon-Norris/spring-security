package com.learn.spring_security.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final AuthEntryPointJwt authEntryPointJwt;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, AuthEntryPointJwt authEntryPointJwt) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.authEntryPointJwt = authEntryPointJwt;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(c -> c.requestMatchers("/user/create").permitAll());
        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(e -> e.authenticationEntryPoint(authEntryPointJwt));
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
}
