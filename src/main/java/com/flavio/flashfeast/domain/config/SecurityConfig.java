package com.flavio.flashfeast.domain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/users",
                        "/api/v1/users/auth",
                        "/api/v1/companies",
                        "/api/v1/companies/auth"
                )
                .permitAll()

                .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/menus",
                        "/api/v1/menus/companies/{idCompany}",
                        "/api/v1/menus/{idMenu}/companies/{idCompany}"
                )
                .permitAll()

                .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/orders/companies/{idCompany}/menus/{idMenu}/users/{idUser}"
                )
                .hasAuthority("ROLE_USER")

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
