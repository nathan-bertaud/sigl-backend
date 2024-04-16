package com.sigl.sigl.service;

import com.sigl.sigl.dto.JwtResponse;
import com.sigl.sigl.dto.UserDetailsDto;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import com.sigl.sigl.dto.LoginForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticateUserSuccess() {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("test@example.com");
        loginForm.setPassword("pass");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setId(0L);

        // Mocking
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetailsDto userDetailsDto = new UserDetailsDto(new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities), user);

        Mockito.when(
                jwtUtils.generateToken(userDetailsDto.getUserDetails())
        ).thenReturn(
                "mockedToken"
        );

        Mockito.when(
                passwordEncoder.matches(user.getPassword(),user.getPassword())
        ).thenReturn(
                true
        );

        Mockito.when(
                userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        Mockito.when(
                roleService.determineUserRoles(0L)
        ).thenReturn(
                authorities
        );

        // Test
        ResponseEntity<Object> response = this.authService.authenticateUser(loginForm);

        // Assertion
        assertEquals(ResponseEntity.ok(new JwtResponse("mockedToken", userDetailsDto.getUser().getFirstName(), userDetailsDto.getUser().getName())), response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockedToken", ((JwtResponse) response.getBody()).getToken());
    }

    @Test
    public void testAuthenticateUserFail() {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail("test@example.com");
        loginForm.setPassword("pass");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setId(0L);

        Mockito.when(
                userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        // Test
        ResponseEntity<Object> response = this.authService.authenticateUser(loginForm);

        // Verif
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Erreur d'authentification : identifiants invalides", response.getBody());
    }
}
