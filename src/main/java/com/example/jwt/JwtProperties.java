package com.example.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

  private String issuerUri;
  private String jwkSetUri;
  private final JwtValidation validation = new JwtValidation();

  @Data
  public static class JwtValidation {

    private boolean enabled;

  }

}
