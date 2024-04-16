package com.sigl.sigl.conf;

import com.sigl.sigl.service.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
  private JwtUtils jwtUtils;

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
      throws IOException, ServletException {
    String token = this.jwtUtils.resolveToken((HttpServletRequest) req);
    try {
      if (token != null && this.jwtUtils.validateToken(token)) {
        Authentication auth =  this.jwtUtils.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    filterChain.doFilter(req, res);
  }
}