package com.example.api.dto;

import org.springframework.security.oauth2.jwt.Jwt;

public record UserInfoDTO(
  String username,
  String email,
  String givenName,
  String lastName
) {

  public static UserInfoDTO from(Jwt jwt) {
    return new UserInfoDTO(
      jwt.getClaimAsString("preferred_username"),
      jwt.getClaimAsString("email"),
      jwt.getClaimAsString("given_name"),
      jwt.getClaimAsString("last_name"));
  }

}
