package com.sigl.sigl.service;

import com.sigl.sigl.model.HumanResources;
import com.sigl.sigl.repository.HumanResourcesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HumanResourcesServiceTest {

    @Mock
    private HumanResourcesRepository humanResourcesRepository;

    @InjectMocks
    private HumanResourcesService humanResourcesService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddHRSuccess() {
        // Init
        HumanResources humanResources = createHRTest();
        Mockito.when(
                this.humanResourcesRepository.findByEmail(humanResources.getEmail()))
        .thenReturn(
                Optional.empty()
        );

        // Action
        this.humanResourcesService.addHumanResources(humanResources);

        // Verif
        Mockito.verify(this.humanResourcesRepository, times(1)).findByEmail(humanResources.getEmail());
        verify(humanResourcesRepository, times(1)).save(humanResources);
        Mockito.verifyNoMoreInteractions(this.humanResourcesRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddHRDuplicateEmail() {
        // Init
        HumanResources humanResources = createHRTest();
        Mockito.when(
                humanResourcesRepository.findByEmail(humanResources.getEmail())
        ).thenReturn(
                Optional.of(humanResources)
        );

        // Actions
        humanResourcesService.addHumanResources(humanResources);

        // Verif --> Exception
    }

    @Test(expected = RuntimeException.class)
    public void testAddHRRepositoryError() {
        // Init
        HumanResources humanResources = createHRTest();
        Mockito.when(
                humanResourcesRepository.findByEmail(humanResources.getEmail())
        ).thenReturn(
                Optional.empty()
        );
        Mockito.doThrow(
                new RuntimeException("Repository error")
        ).when(humanResourcesRepository).save(humanResources);

        // Actions
        humanResourcesService.addHumanResources(humanResources);

        // Verif --> Exception
    }

    /**
     * Méthode pour créer un nouvel HR qui va permettre les tests
     * @return Le HR test
     */
    private static HumanResources createHRTest() {
        HumanResources humanResources = new HumanResources();
        humanResources.setEmail("test@test.com");
        humanResources.setFirstName("TestFirstName");
        humanResources.setName("TestName");
        humanResources.setPassword("TestPassword");
        humanResources.setCompany("ESEO");
        return humanResources;
    }
}
