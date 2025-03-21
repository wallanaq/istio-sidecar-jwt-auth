package com.example.jwt;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtSidecarDecoder implements JwtDecoder {

  @Override
  public Jwt decode(String token) throws JwtException {

    try {

      var jwt = SignedJWT.parse(token);
      var claims = jwt.getJWTClaimsSet();

      return Jwt
        .withTokenValue(token)
        .headers((Map<String, Object> map) -> map.putAll(jwt.getHeader().toJSONObject()))
        .claims((Map<String, Object> map) -> map.putAll(claims.getClaims()))
        .issuedAt(toInstant(claims.getIssueTime()))
        .expiresAt(toInstant(claims.getExpirationTime()))
        .build();

    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid JWT", e);
    }

  }

  private Instant toInstant(Date date) {
    return date != null ? date.toInstant() : null;
  }

}
