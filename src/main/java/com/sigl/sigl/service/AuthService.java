package com.sigl.sigl.service;

import com.sigl.sigl.dto.JwtResponse;
import com.sigl.sigl.dto.LoginForm;
import com.sigl.sigl.dto.UserDetailsDto;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleService roleService;

  @Autowired
  private UserService userService;

  @Autowired
  private JwtUtils jwtUtils;

  public ResponseEntity<Object> authenticateUser(LoginForm loginForm) {
    try {
      UserDetailsDto userDetailsDto = loadUserByEmail(loginForm.getEmail(),loginForm.getPassword());
      String jwt = this.jwtUtils.generateToken(userDetailsDto.getUserDetails());
      return ResponseEntity.ok(new JwtResponse(jwt,userDetailsDto.getUser().getFirstName(),userDetailsDto.getUser().getName()));
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erreur d'authentification : identifiants invalides");
    }
  }

  public UserDetailsDto loadUserByEmail(String email, String passwordTry) throws UsernameNotFoundException {
    Optional<User> userOptional = userRepository.findByEmail(email);
    User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'adresse e-mail: " + email));
      String passwordUser = user.getPassword();
    if (!passwordEncoder.matches(passwordTry,passwordUser)) {
      throw new BadCredentialsException("Identifiants invalides");
    }
    this.userService.updateLastConnexionUser(user);
    return new UserDetailsDto(new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        this.roleService.determineUserRoles(user.getId())
    ), user);
  }


}
