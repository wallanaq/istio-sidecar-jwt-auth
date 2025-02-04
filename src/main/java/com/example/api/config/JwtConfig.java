package com.example.api.config;

import com.example.api.jwt.JwtSidecarDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

  @Value("${jwt.jwk-set-uri}")
  private String jwkSetUri;

  @Value("${jwt.verify:true}")
  private boolean verifyJwt;

  @Bean
  public JwtDecoder jwtDecoder() {
    return verifyJwt ? NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build() : new JwtSidecarDecoder();
  }
}
