package com.sigl.sigl.service;

import com.sigl.sigl.model.EducationalTutor;
import com.sigl.sigl.repository.EducationalTutorRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.times;

public class EducationalTutorServiceTest {

    @Mock
    private EducationalTutorRepository educationalTutorRepository;

    @InjectMocks
    private EducationalTutorService educationalTutorService;

    @Before
    public void setUp() {MockitoAnnotations.initMocks(this); }

    @Test
    public void testAddEducationalTutorSuccess(){
        //Init
        EducationalTutor educationalTutorTest = createEducationalTutor();

        //Mock
        Mockito.when(
                this.educationalTutorRepository.findByEmail(educationalTutorTest.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        this.educationalTutorService.addEducationalTutor(educationalTutorTest);

        //Verification
        Mockito.verify(this.educationalTutorRepository, times(1)).findByEmail(educationalTutorTest.getEmail());
        Mockito.verify(this.educationalTutorRepository, times(1)).save(educationalTutorTest);
        Mockito.verifyNoMoreInteractions(this.educationalTutorRepository);

    }

    @Test(expected = RuntimeException.class)
    public void testAddEducationalTutorFailExisting(){
        //Init
        EducationalTutor educationalTutorTest = createEducationalTutor();

        //Mock
        Mockito.when(
                this.educationalTutorRepository.findByEmail(educationalTutorTest.getEmail())
        ).thenReturn(
                Optional.of(educationalTutorTest)
        );

        //Traitement
        this.educationalTutorService.addEducationalTutor(educationalTutorTest);

        //Verification
        Mockito.verify(this.educationalTutorRepository, times(1)).findByEmail(educationalTutorTest.getEmail());
        Mockito.verifyNoMoreInteractions(this.educationalTutorRepository);

    }

    @Test(expected = RuntimeException.class)
    public void testAddEducationalTutorFailSave(){
        //Init
        EducationalTutor educationalTutorTest = createEducationalTutor();

        //Mock
        Mockito.when(
                this.educationalTutorRepository.findByEmail(educationalTutorTest.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        Mockito.when(
                this.educationalTutorRepository.save(educationalTutorTest)
        ).thenThrow(
                new RuntimeException("La sauvegarde a échoué !")
        );

        //Traitement
        this.educationalTutorService.addEducationalTutor(educationalTutorTest);

        //Verification
        Mockito.verify(this.educationalTutorRepository, times(1)).findByEmail(educationalTutorTest.getEmail());
        Mockito.verify(this.educationalTutorRepository, times(1)).save(educationalTutorTest);
        Mockito.verifyNoMoreInteractions(this.educationalTutorRepository);

    }

    private EducationalTutor createEducationalTutor(){
        EducationalTutor educationalTutor = new EducationalTutor();
        educationalTutor.setName("TestName");
        educationalTutor.setPassword("TestPassword");
        educationalTutor.setFirstName("TestFirstName");
        educationalTutor.setEmail("test@email.com");

        return educationalTutor;
    }

}