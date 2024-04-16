package com.sigl.sigl.service;

import com.sigl.sigl.dto.ChangePasswordForm;
import com.sigl.sigl.model.Event;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.UserRepository;
import jakarta.persistence.NoResultException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUserSuccess() {
        // Init
        User user = createUserTest();
        Mockito.when(
                this.userRepository.findByEmail(user.getEmail()))
        .thenReturn(
                Optional.empty()
        );

        // Action
        this.userService.addUser(user);

        // Verif
        Mockito.verify(this.userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddUserDuplicateEmail() {
        // Init
        User user = createUserTest();
        Mockito.when(
                userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        // Actions
        userService.addUser(user);

        // Verif --> Exception
    }

    @Test(expected = RuntimeException.class)
    public void testAddUserRepositoryError() {
        // Init
        User user = createUserTest();
        Mockito.when(
                userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.empty()
        );
        Mockito.doThrow(
                new RuntimeException("Repository error")
        ).when(userRepository).save(user);

        // Actions
        userService.addUser(user);

        // Verif --> Exception
    }

    @Test
    public void testDeleteUserByIDSuccess(){
        //Init
        User user = createUserTest();
        Mockito.when(
                this.userRepository.findById(0L)
        ).thenReturn(
                Optional.of(user)
        );

        // Actions
        this.userService.deleteUserById(0L);

        // Verif
        Mockito.verify(this.userRepository, times(1)).findById(0L);
        Mockito.verify(this.userRepository, times(1)).deleteById(0L);
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteUserByIDFail(){
        //Init
        User user = createUserTest();
        Mockito.when(
                this.userRepository.findById(0L)
        ).thenReturn(
                Optional.empty()
        );

        // Actions
        this.userService.deleteUserById(0L);

        // Verif --> Exception
    }

    @Test
    public void testSetPasswordSuccess(){
        //Init
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setPassword("TestPassword");
        changePasswordForm.setNewPassword("TestPassword");
        User user = createUserTest();

        Mockito.when(
                this.userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        Mockito.when(
                this.passwordEncoder.matches(changePasswordForm.getPassword(),user.getPassword())
        ).thenReturn(
                true
        );

        //Actions
        ResponseEntity<Void> responseEntity = this.userService.setPassword(user.getEmail(), changePasswordForm);

        // Vérificaton
        Assert.assertEquals("La réponse attendue n'est pas correcte",ResponseEntity.noContent().build(), responseEntity);
        Mockito.verify(this.userRepository,times(1)).findByEmail(user.getEmail());
        Mockito.verify(this.userRepository,times(1)).save(user);
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test(expected = NoResultException.class)
    public void testSetPasswordFailUserNUll(){
        //Init
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setPassword("TestPassword");
        changePasswordForm.setNewPassword("TestPassword");
        User user = createUserTest();

        Mockito.when(
                this.userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Actions
        ResponseEntity<Void> responseEntity = this.userService.setPassword(user.getEmail(), changePasswordForm);

        // Vérificaton --> Exception
    }

    @Test(expected = RuntimeException.class)
    public void testSetPasswordFailRuntime(){
        //Init
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setPassword("TestPassword");
        changePasswordForm.setNewPassword("TestPassword");
        User user = createUserTest();

        Mockito.when(
                this.userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        Mockito.when(
                this.passwordEncoder.matches(changePasswordForm.getPassword(),user.getPassword())
        ).thenReturn(
                false
        );

        //Actions
        ResponseEntity<Void> responseEntity = this.userService.setPassword(user.getEmail(), changePasswordForm);

        // Vérificaton --> Exception
    }

    @Test
    public void testUpdateDateLastConnexionSuccess(){
        //Init
        User user = createUserTest();

        // Traitement
        this.userService.updateLastConnexionUser(user);

        Mockito.verify(this.userRepository,times(1)).save(user);
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test(expected = NoResultException.class)
    public void testUpdateDateLastConnexionFail(){

        // Traitement
        this.userService.updateLastConnexionUser(null);

        //Verif --> exception
    }

    @Test
    public void testGetUserIdByEmailSuccess(){
        //Initialisation
        User user = createUserTest();

        Mockito.when(
                this.userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.of(user)
        );

        //Traitement
        long idObtenu = this.userService.getUserIdByEmail(user.getEmail());

        //Verification
        Assert.assertTrue("Les IDs sont différents",user.getId().equals(idObtenu));
        Mockito.verify(this.userRepository,times(1)).findByEmail(user.getEmail());
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserIdByEmailFail(){
        //Initialisation
        User user = createUserTest();

        Mockito.when(
                this.userRepository.findByEmail(user.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        long idObtenu = this.userService.getUserIdByEmail(user.getEmail());

        //Verification
        Mockito.verify(this.userRepository,times(1)).findByEmail(user.getEmail());
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    public void testSetPasswordByIdSuccess(){
        //Init
        User user = createUserTest();
        User userAfter = user;
        userAfter.setPassword(passwordEncoder.encode(user.getPassword()));

        Mockito.when(
                this.userRepository.findById(user.getId())
        ).thenReturn(
                Optional.of(user)
        );

        //Traitement
        this.userService.setPasswordById(user.getId(), user.getPassword());

        //Verification
        Mockito.verify(this.userRepository, times(1)).findById(user.getId());
        Mockito.verify(this.userRepository, times(1)).save(userAfter);
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testSetPasswordByIdFailNoUser(){
        //Init
        User user = createUserTest();

        Mockito.when(
                this.userRepository.findById(user.getId())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        this.userService.setPasswordById(user.getId(), user.getPassword());

        //Verification
        Mockito.verify(this.userRepository, times(1)).findById(user.getId());
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test
    public void getUsersSuccess(){
        //Init
        Pageable pageable = ApprenticeServiceTests.createPageable();
        User user = createUserTest();
        List<User> userList = new ArrayList<>();
        userList.add(user);

        Page<User> usersToRetrieve = new PageImpl<>(userList,pageable,userList.size());

        Mockito.when(
                this.userRepository.findAll(pageable)
        ).thenReturn(
                usersToRetrieve
        );

        //Traitement
        Page<User> usersTested = this.userService.getUsers(pageable);

        //Vérification
        Assert.assertTrue("Le retour n'est pas celui attendu", usersToRetrieve.equals(usersTested));
        Mockito.verify(this.userRepository, times(1)).findAll(pageable);
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    @Test(expected = RuntimeException.class)
    public void getUsersFailRuntimeException(){
        //Init
        Pageable pageable = ApprenticeServiceTests.createPageable();

        Mockito.when(
                this.userRepository.findAll(pageable)
        ).thenThrow(
                RuntimeException.class
        );

        //Traitement
        Page<User> usersTested = this.userService.getUsers(pageable);

        //Vérification
        Mockito.verify(this.userRepository, times(1)).findAll(pageable);
        Mockito.verifyNoMoreInteractions(this.userRepository);
    }

    /**
     * Méthode pour créer un nouvel user qui va permettre les tests
     * @return Le user test
     */
    private static User createUserTest() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("TestFirstName");
        user.setName("TestName");
        user.setPassword("TestPassword");
        user.setId(145L);
        return user;
    }
}
