package com.example.jwt.decoder;

import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

@Slf4j
public class SimpleJwtDecoder implements JwtDecoder {

  @Override
  public Jwt decode(String token) throws JwtException {

    try {

      var jwt = SignedJWT.parse(token);
      var header = jwt.getHeader();
      var claims = jwt.getJWTClaimsSet();

      return Jwt
        .withTokenValue(token)
        .headers(map -> map.putAll(header.toJSONObject()))
        .claims(map -> map.putAll(claims.getClaims()))
        .issuedAt(toInstant(claims.getIssueTime()))
        .expiresAt(toInstant(claims.getExpirationTime()))
        .build();

    } catch (ParseException e) {
      throw new JwtException("Invalid JWT", e);
    }

  }

  private Instant toInstant(Date date) {
    return date != null ? date.toInstant() : null;
  }

}
