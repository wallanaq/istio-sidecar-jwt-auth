package com.example.api.dto;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponse(
  HttpStatus code,
  String message,
  Instant timestamp
) {}
