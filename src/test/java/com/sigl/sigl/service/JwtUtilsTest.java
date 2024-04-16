package com.sigl.sigl.service;

import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtilsMock;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    private static final long EXPIRATION_TIME = 864_000_000; // 10 jours
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //Ce test peut ne pas passer du premier coup car le token dépend de la date, qui ne peut pas être équivalente
    // à 100% de la date init, j'ai tronqué le token pour enlever la partie dépendante des sec et milisec
    @Test
    public void generateTokenTest(){
        // Init
        User user = createUserTestJwt();

        Date now  = new Date();

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);

        //Actions
        String token = enleverDernierePartieToken(this.jwtUtilsMock.generateToken(userDetails));

        Assert.assertEquals("Le token n'est pas équivalent", enleverDernierePartieToken(Jwts.builder()
                .claim("sub",userDetails.getUsername())
                .claim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact()),token);
    }

    @Test
    public void testResolveTokenSuccess(){
        // Init
        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);

        Mockito.when(
                httpServletRequestMock.getHeader("Authorization")
        ).thenReturn(
                "Bearer tokenAttendu"
        );

        // Action
        String tokenObtenu = this.jwtUtilsMock.resolveToken(httpServletRequestMock);

        // Vérification
        Assert.assertEquals("Le token n'est pas celui attendu","tokenAttendu",tokenObtenu);
        Mockito.verify(httpServletRequestMock,times(1)).getHeader("Authorization");
        Mockito.verifyNoMoreInteractions(httpServletRequestMock);
    }

    @Test
    public void testResolveTokenFailWrongStart(){
        // Init
        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);

        // On a changé Bearer pour Berer
        Mockito.when(
                httpServletRequestMock.getHeader("Authorization")
        ).thenReturn(
                "Berer tokenAttendu"
        );

        // Action
        String tokenObtenu = this.jwtUtilsMock.resolveToken(httpServletRequestMock);

        // Vérification
        Assert.assertNotEquals("Le token est celui attendu","tokenAttendu",tokenObtenu);
        Mockito.verify(httpServletRequestMock,times(1)).getHeader("Authorization");
        Mockito.verifyNoMoreInteractions(httpServletRequestMock);
    }

    @Test
    public void testResolveTokenFailTokenNull(){
        // Init
        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);

        // On a changé Bearer pour Berer
        Mockito.when(
                httpServletRequestMock.getHeader("Authorization")
        ).thenReturn(
                null
        );

        // Action
        String tokenObtenu = this.jwtUtilsMock.resolveToken(httpServletRequestMock);

        // Vérification
        Assert.assertNotEquals("Le token est celui attendu","tokenAttendu",tokenObtenu);
        Mockito.verify(httpServletRequestMock,times(1)).getHeader("Authorization");
        Mockito.verifyNoMoreInteractions(httpServletRequestMock);
    }

    @Test
    public void testGetAuthenticationSuccess(){
        //Init
        User user = createUserTestJwt();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);

        String token = this.jwtUtilsMock.generateToken(userDetails);

        Authentication authenticationNeeded = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        Mockito.when(
                this.userRepository.findByEmail(anyString())
        ).thenReturn(
                Optional.of(user)
        );

        Mockito.when(
                this.roleService.determineUserRoles(Optional.of(user).get().getId())
        ).thenReturn(
                authorities
        );

        //Traitement
        Authentication authenticationObtained = this.jwtUtilsMock.getAuthentication(token);

        //Verification
        Assert.assertTrue("Authentifications différentes", authenticationNeeded.equals(authenticationObtained));
        Mockito.verify(this.userRepository,times(1)).findByEmail(anyString());
        Mockito.verifyNoMoreInteractions(this.userRepository);
        Mockito.verify(roleService,times(1)).determineUserRoles(Optional.of(user).get().getId());
        Mockito.verifyNoMoreInteractions(roleService);
    }

    /**
     * Fonction permettant de retirer la dernière partie de notre token qui prend en compte la date
     * car la date ne pourra pas être équivalent à cause des sec et milisec
     * @param token notre token généré
     * @return notre token sans la dernière partie
     */
    private static String enleverDernierePartieToken(String token) {
        int dernierPoint = token.lastIndexOf('.');

        if (dernierPoint != -1) {
            return token.substring(0, dernierPoint);
        } else {
            // Pas de point trouvé, retourner la chaîne d'origine
            return token;
        }
    }

    /**
     * Fonction permettant de créer un user pour cette classe de test
     * @return le user créé
     */
    private User createUserTestJwt(){
        User user = new User();
        user.setName("test");
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setId(0L);

        return user;
    }
}
