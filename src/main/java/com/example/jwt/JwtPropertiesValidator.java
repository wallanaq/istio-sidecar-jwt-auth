package com.example.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtPropertiesValidator implements InitializingBean {

  private final JwtProperties properties;

  @Override
  public void afterPropertiesSet() throws Exception {
    if (properties.getValidation().isEnabled() && properties.getJwkSetUri() == null) {
      throw new IllegalStateException("JWK Set URI is required when JWT validation is \"true\"");
    }
  }
}
