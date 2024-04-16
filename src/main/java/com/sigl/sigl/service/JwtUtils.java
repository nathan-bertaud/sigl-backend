package com.sigl.sigl.service;

import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtUtils {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleService roleService;

  public static final long EXPIRATION_TIME = 864_000_000; // 10 jours
  private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


  public String generateToken(UserDetails userDetails) {
    Date now = new Date();
    Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

    List<String> roles = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    return Jwts.builder()
        .claim("sub",userDetails.getUsername())
        .claim("roles", roles)
        .setIssuedAt(now)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    Optional<User> user = this.userRepository.findByEmail( Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject());
    Set<GrantedAuthority> authorities = new HashSet<>();
    String username = null;
    String password  = null;
    if(user.isPresent()){
      username = user.get().getEmail();
      password = user.get().getPassword();
      authorities = this.roleService.determineUserRoles(user.get().getId());
    }
    UserDetails userDetails = new org.springframework.security.core.userdetails.User(
        username,
        password,
        authorities
    );
    return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
  }

   public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  public boolean validateToken(String token) throws Exception {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
      if (claims.getBody().getExpiration().before(new Date())) {
        return false;
      }
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new Exception("Expired or invalid JWT token");
    }
  }
}
