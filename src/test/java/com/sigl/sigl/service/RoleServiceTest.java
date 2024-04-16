package com.sigl.sigl.service;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.testng.Assert;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.times;

public class RoleServiceTest {

    @InjectMocks
    RoleService roleService;

    @Mock
    ApprenticeRepository apprenticeRepositoryMock;

    @Mock
    HumanResourcesRepository humanResourcesRepositoryMock;

    @Mock
    AdministratorRepository administratorRepositoryMock;

    @Mock
    ApprenticeMentorRepository apprenticeMentorRepositoryMock;

    @Mock
    CoordinatorRepository coordinatorRepositoryMock;

    @Mock
    EducationalTutorRepository educationalTutorRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void determineUserRolesUser(){
        //Init
        setIsPresentMocks(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty());

        //Actions
        Set<GrantedAuthority> authorities = this.roleService.determineUserRoles(0L);
        boolean containUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean containApprentice = authorities.contains(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        boolean containHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
        boolean containAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean containMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
        boolean containCoordinator = authorities.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        boolean containEducationalTutor = authorities.contains(new SimpleGrantedAuthority("ROLE_TUTOR"));

        //Verif
        Assert.assertEquals(containUser,true,"Le rôle user n'est pas bien ajouté");
        Assert.assertEquals(containApprentice,false,"Le rôle apprenti n'est pas bien ajouté");
        Assert.assertEquals(containHR,false,"Le rôle HR n'est pas bien ajouté");
        Assert.assertEquals(containAdmin,false,"Le rôle admin n'est pas bien ajouté");
        Assert.assertEquals(containMentor,false,"Le rôle MA n'est pas bien ajouté");
        Assert.assertEquals(containCoordinator,false,"Le rôle coordinateur n'est pas bien ajouté");
        Assert.assertEquals(containEducationalTutor,false,"Le rôle tuteur n'est pas bien ajouté");
        verifyMock();
    }

    @Test
    public void determineUserRolesApprentice(){
        //Init
        setIsPresentMocks(Optional.empty(),Optional.empty(),Optional.empty(),Optional.of(new Apprentice()), Optional.empty(),Optional.empty());

        //Actions
        Set<GrantedAuthority> authorities = this.roleService.determineUserRoles(0L);
        boolean containUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean containApprentice = authorities.contains(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        boolean containHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
        boolean containAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean containMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
        boolean containCoordinator = authorities.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        boolean containEducationalTutor = authorities.contains(new SimpleGrantedAuthority("ROLE_TUTOR"));

        //Verif
        Assert.assertEquals(containUser,true,"Le rôle user n'est pas bien ajouté");
        Assert.assertEquals(containApprentice,true,"Le rôle apprenti n'est pas bien ajouté");
        Assert.assertEquals(containHR,false,"Le rôle HR n'est pas bien ajouté");
        Assert.assertEquals(containAdmin,false,"Le rôle admin n'est pas bien ajouté");
        Assert.assertEquals(containMentor,false,"Le rôle MA n'est pas bien ajouté");
        Assert.assertEquals(containCoordinator,false,"Le rôle coordinateur n'est pas bien ajouté");
        Assert.assertEquals(containEducationalTutor,false,"Le rôle tuteur n'est pas bien ajouté");
        verifyMock();
    }

    @Test
    public void determineUserRolesHR(){
        //Init
        setIsPresentMocks(Optional.empty(),Optional.empty(),Optional.of(new Apprentice()),Optional.empty(),Optional.empty(),Optional.empty());

        //Actions
        Set<GrantedAuthority> authorities = this.roleService.determineUserRoles(0L);
        boolean containUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean containApprentice = authorities.contains(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        boolean containHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
        boolean containAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean containMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
        boolean containCoordinator = authorities.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        boolean containEducationalTutor = authorities.contains(new SimpleGrantedAuthority("ROLE_TUTOR"));

        //Verif
        Assert.assertEquals(containUser,true,"Le rôle user n'est pas bien ajouté");
        Assert.assertEquals(containApprentice,false,"Le rôle apprenti n'est pas bien ajouté");
        Assert.assertEquals(containHR,true,"Le rôle HR n'est pas bien ajouté");
        Assert.assertEquals(containAdmin,false,"Le rôle admin n'est pas bien ajouté");
        Assert.assertEquals(containMentor,false,"Le rôle MA n'est pas bien ajouté");
        Assert.assertEquals(containCoordinator,false,"Le rôle coordinateur n'est pas bien ajouté");
        Assert.assertEquals(containEducationalTutor,false,"Le rôle tuteur n'est pas bien ajouté");
        verifyMock();
    }

    @Test
    public void determineUserRolesAdmin(){
        //Init
        setIsPresentMocks(Optional.empty(),Optional.of(new Apprentice()),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty());

        //Actions
        Set<GrantedAuthority> authorities = this.roleService.determineUserRoles(0L);
        boolean containUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean containApprentice = authorities.contains(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        boolean containHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
        boolean containAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean containMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
        boolean containCoordinator = authorities.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        boolean containEducationalTutor = authorities.contains(new SimpleGrantedAuthority("ROLE_TUTOR"));

        //Verif
        Assert.assertEquals(containUser,true,"Le rôle user n'est pas bien ajouté");
        Assert.assertEquals(containApprentice,false,"Le rôle apprenti n'est pas bien ajouté");
        Assert.assertEquals(containHR,false,"Le rôle HR n'est pas bien ajouté");
        Assert.assertEquals(containAdmin,true,"Le rôle admin n'est pas bien ajouté");
        Assert.assertEquals(containMentor,false,"Le rôle MA n'est pas bien ajouté");
        Assert.assertEquals(containCoordinator,false,"Le rôle coordinateur n'est pas bien ajouté");
        Assert.assertEquals(containEducationalTutor,false,"Le rôle tuteur n'est pas bien ajouté");
        verifyMock();
    }

    @Test
    public void determineUserRolesApprenticeMentor(){
        //Init
        setIsPresentMocks(Optional.of(new Apprentice()),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty());

        //Actions
        Set<GrantedAuthority> authorities = this.roleService.determineUserRoles(0L);
        boolean containUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean containApprentice = authorities.contains(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        boolean containHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
        boolean containAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean containMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
        boolean containCoordinator = authorities.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        boolean containEducationalTutor = authorities.contains(new SimpleGrantedAuthority("ROLE_TUTOR"));

        //Verif
        Assert.assertEquals(containUser,true,"Le rôle user n'est pas bien ajouté");
        Assert.assertEquals(containApprentice,false,"Le rôle apprenti n'est pas bien ajouté");
        Assert.assertEquals(containHR,false,"Le rôle HR n'est pas bien ajouté");
        Assert.assertEquals(containAdmin,false,"Le rôle admin n'est pas bien ajouté");
        Assert.assertEquals(containMentor,true,"Le rôle MA n'est pas bien ajouté");
        Assert.assertEquals(containCoordinator,false,"Le rôle coordinateur n'est pas bien ajouté");
        Assert.assertEquals(containEducationalTutor,false,"Le rôle tuteur n'est pas bien ajouté");
        verifyMock();
    }

    @Test
    public void determineUserRolesCoordinator(){
        //Init
        setIsPresentMocks(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.of(new Apprentice()),Optional.empty());

        //Actions
        Set<GrantedAuthority> authorities = this.roleService.determineUserRoles(0L);
        boolean containUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean containApprentice = authorities.contains(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        boolean containHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
        boolean containAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean containMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
        boolean containCoordinator = authorities.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        boolean containEducationalTutor = authorities.contains(new SimpleGrantedAuthority("ROLE_TUTOR"));

        //Verif
        Assert.assertEquals(containUser,true,"Le rôle user n'est pas bien ajouté");
        Assert.assertEquals(containApprentice,false,"Le rôle apprenti n'est pas bien ajouté");
        Assert.assertEquals(containHR,false,"Le rôle HR n'est pas bien ajouté");
        Assert.assertEquals(containAdmin,false,"Le rôle admin n'est pas bien ajouté");
        Assert.assertEquals(containMentor,false,"Le rôle MA n'est pas bien ajouté");
        Assert.assertEquals(containCoordinator,true,"Le rôle coordinateur n'est pas bien ajouté");
        Assert.assertEquals(containEducationalTutor,false,"Le rôle tuteur n'est pas bien ajouté");
        verifyMock();
    }

    @Test
    public void determineUserRolesEducationalTutor(){
        //Init
        setIsPresentMocks(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.of(new Apprentice()));

        //Actions
        Set<GrantedAuthority> authorities = this.roleService.determineUserRoles(0L);
        boolean containUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
        boolean containApprentice = authorities.contains(new SimpleGrantedAuthority("ROLE_APPRENTI"));
        boolean containHR = authorities.contains(new SimpleGrantedAuthority("ROLE_HR"));
        boolean containAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean containMentor = authorities.contains(new SimpleGrantedAuthority("ROLE_MENTOR"));
        boolean containCoordinator = authorities.contains(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
        boolean containEducationalTutor = authorities.contains(new SimpleGrantedAuthority("ROLE_TUTOR"));

        //Verif
        Assert.assertEquals(containUser,true,"Le rôle user n'est pas bien ajouté");
        Assert.assertEquals(containApprentice,false,"Le rôle apprenti n'est pas bien ajouté");
        Assert.assertEquals(containHR,false,"Le rôle HR n'est pas bien ajouté");
        Assert.assertEquals(containAdmin,false,"Le rôle admin n'est pas bien ajouté");
        Assert.assertEquals(containMentor,false,"Le rôle MA n'est pas bien ajouté");
        Assert.assertEquals(containCoordinator,false,"Le rôle coordinateur n'est pas bien ajouté");
        Assert.assertEquals(containEducationalTutor,true,"Le rôle tuteur n'est pas bien ajouté");
        verifyMock();
    }

    /**
     * Fonction afin de vérifier le nombre d'appel à la fonction isPresent des mocks
     */
    private void verifyMock() {
        Mockito.verify(this.apprenticeRepositoryMock, times(1)).findById(0L);
        Mockito.verifyNoMoreInteractions(this.apprenticeRepositoryMock);
        Mockito.verify(this.humanResourcesRepositoryMock, times(1)).findById(0L);
        Mockito.verifyNoMoreInteractions(this.humanResourcesRepositoryMock);
        Mockito.verify(this.administratorRepositoryMock, times(1)).findById(0L);
        Mockito.verifyNoMoreInteractions(this.administratorRepositoryMock);
        Mockito.verify(this.apprenticeMentorRepositoryMock, times(1)).findById(0L);
        Mockito.verifyNoMoreInteractions(this.apprenticeMentorRepositoryMock);
        Mockito.verify(this.coordinatorRepositoryMock, times(1)).findById(0L);
        Mockito.verifyNoMoreInteractions(this.coordinatorRepositoryMock);
        Mockito.verify(this.educationalTutorRepository, times(1)).findById(0L);
        Mockito.verifyNoMoreInteractions(this.educationalTutorRepository);
    }

    /**
     * Fonction afin d'init les valeurs de la fonction isPresent pour nos mocks
     * @param appMen ApprenticeMentor
     * @param admOpt AdministratorOptional
     * @param humRes HumanResources
     * @param appOpt ApprenticeOptional
     * @param cooOpt CoordinatorOptional
     * @param eduTut EducationalTutor
     */
    private void setIsPresentMocks(Optional appMen, Optional admOpt, Optional humRes, Optional appOpt, Optional cooOpt, Optional eduTut) {
        Mockito.when(
                this.apprenticeRepositoryMock.findById(0L)
        ).thenReturn(
                appOpt
        );
        Mockito.when(
                this.humanResourcesRepositoryMock.findById(0L)
        ).thenReturn(
                humRes
        );
        Mockito.when(
                this.administratorRepositoryMock.findById(0L)
        ).thenReturn(
                admOpt
        );
        Mockito.when(
                this.apprenticeMentorRepositoryMock.findById(0L)
        ).thenReturn(
                appMen
        );
        Mockito.when(
                this.coordinatorRepositoryMock.findById(0L)
        ).thenReturn(
                cooOpt
        );
        Mockito.when(
                this.educationalTutorRepository.findById(0L)
        ).thenReturn(
                eduTut
        );
    }
}
