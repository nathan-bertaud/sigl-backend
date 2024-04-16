package com.sigl.sigl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class JwtResponse {
  private String token;
  private String firstName;
  private String lastName;
}
