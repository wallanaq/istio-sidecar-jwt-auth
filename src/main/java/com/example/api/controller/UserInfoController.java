package com.example.api.controller;

import com.example.api.dto.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userinfo")
public class UserInfoController {

  @GetMapping
  public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok().body(UserInfoResponse.from(jwt));
  }

}
