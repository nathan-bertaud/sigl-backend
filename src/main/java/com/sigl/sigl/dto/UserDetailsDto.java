package com.sigl.sigl.dto;

import com.sigl.sigl.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class UserDetailsDto {

  private UserDetails userDetails;

  private User user;

}
