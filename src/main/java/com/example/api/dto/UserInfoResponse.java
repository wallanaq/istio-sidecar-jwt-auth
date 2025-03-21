package com.example.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.oauth2.jwt.Jwt;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserInfoResponse(
  @JsonProperty(required = true)
  String username,
  String email,
  @JsonProperty("given_name")
  String givenName,
  @JsonProperty("last_name")
  String lastName
) {

  public static UserInfoResponse from(Jwt jwt) {
    return new UserInfoResponse(
      jwt.getClaimAsString("preferred_username"),
      jwt.getClaimAsString("email"),
      jwt.getClaimAsString("given_name"),
      jwt.getClaimAsString("family_name"));
  }

}
