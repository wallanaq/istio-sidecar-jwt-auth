package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  private final JwtDecoder jwtDecoder;

  public SecurityConfig(JwtDecoder jwtDecoder) {
    this.jwtDecoder = jwtDecoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authz -> authz
        .requestMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated())
      .oauth2ResourceServer(oauth2 -> oauth2
        .jwt(jwt -> jwt.decoder(jwtDecoder)));

    return http.build();
  }

}
