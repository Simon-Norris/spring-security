package com.learn.spring_security.config.security;

import com.learn.spring_security.base.userManagement.enums.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final AuthEntryPointJwt authEntryPointJwt;

    private final AuthFilter authFilter;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, AuthEntryPointJwt authEntryPointJwt, AuthFilter authFilter) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.authEntryPointJwt = authEntryPointJwt;
        this.authFilter = authFilter;
    }

    private static final String[] WHITE_LIST_APIS = new String[] {
            "api/v1/auth/login",
            "api/v1/auth/register",
            "api/v1/auth/forget-password",
            "api/v1/dashboard"
    };

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(c -> c.requestMatchers(WHITE_LIST_APIS).permitAll());
        http.authorizeHttpRequests(c -> c.requestMatchers("/admin-control").hasRole(RoleType.SUPER_ADMIN.getValue()));
        http.authorizeHttpRequests(c -> c.requestMatchers("/admin").hasAnyRole(RoleType.SUPER_ADMIN.getValue(), RoleType.ADMIN.getValue()));
        http.authorizeHttpRequests(c -> c.requestMatchers("/editor").hasAnyRole(RoleType.EDITOR.getValue(), RoleType.ADMIN.getValue()));
        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(e -> e.authenticationEntryPoint(authEntryPointJwt));
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
}
