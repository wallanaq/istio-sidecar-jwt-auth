package com.example.api.jwt;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.text.ParseException;

public class JwtSidecarDecoder implements JwtDecoder {

  @Override
  public Jwt decode(String token) throws JwtException {

    try {

      var jwt = SignedJWT.parse(token);
      var claims = jwt.getJWTClaimsSet();

      return Jwt
        .withTokenValue(token)
        .headers(map -> map.putAll(jwt.getHeader().toJSONObject()))
        .claims(map -> map.putAll(claims.getClaims()))
        .issuedAt(claims.getIssueTime().toInstant())
        .expiresAt(claims.getExpirationTime().toInstant())
        .build();

    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid JWT", e);
    }

  }

}
