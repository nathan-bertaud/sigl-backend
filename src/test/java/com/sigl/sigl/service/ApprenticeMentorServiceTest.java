package com.sigl.sigl.service;

import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.ApprenticeMentor;
import com.sigl.sigl.repository.ApprenticeMentorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApprenticeMentorServiceTest {

    @Mock
    private ApprenticeMentorRepository apprenticeMentorRepository;

    @InjectMocks
    private ApprenticeMentorService apprenticeMentorService;

    @Before
    public void setUp() {MockitoAnnotations.initMocks(this); }

    @Test
    public void testFindApprenticesSuccess(){
        //Init
        ApprenticeMentor apprenticeMentor = new ApprenticeMentor();
        apprenticeMentor.setEmail("amtest@email.com");

        Apprentice apprentice1 = new Apprentice();
        apprentice1.setEmail("a1@email.com");
        Apprentice apprentice2 = new Apprentice();
        apprentice2.setEmail("a1@email.com");

        List<Apprentice> listApprenticesTest = new ArrayList<>();
        listApprenticesTest.add(apprentice1);
        listApprenticesTest.add(apprentice2);

        List<ApprenticeDetailsDto> list = new ArrayList<>();
        listApprenticesTest.forEach(apprentice -> {
            list.add(ApprenticeDetailsDto.fromApprentice(apprentice));
        });

        apprenticeMentor.setApprentices(listApprenticesTest);

        //Mock
        Mockito.when(
                this.apprenticeMentorRepository.findByEmail(apprenticeMentor.getEmail())
        ).thenReturn(
                Optional.of(apprenticeMentor)
        );

        //Traitement
        List<ApprenticeDetailsDto> listApprentices = this.apprenticeMentorService.findAprentices(apprenticeMentor.getEmail());

        //Verification
        Assert.assertTrue("Les listes sont différentes", listApprentices.equals(list));
        Mockito.verify(this.apprenticeMentorRepository,times(1)).findByEmail(apprenticeMentor.getEmail());
        Mockito.verifyNoMoreInteractions(this.apprenticeMentorRepository);
    }

    @Test
    public void testFindApprenticesSuccessEmpty(){
        //Init
        ApprenticeMentor apprenticeMentor = new ApprenticeMentor();
        apprenticeMentor.setEmail("amtest@email.com");
        List<Apprentice> listApprenticesTest = new ArrayList<>();
        apprenticeMentor.setApprentices(listApprenticesTest);

        //Mock
        Mockito.when(
                this.apprenticeMentorRepository.findByEmail(apprenticeMentor.getEmail())
        ).thenReturn(
                Optional.of(apprenticeMentor)
        );

        //Traitement
        List<ApprenticeDetailsDto> listApprentices = this.apprenticeMentorService.findAprentices(apprenticeMentor.getEmail());

        //Verification
        Assert.assertTrue("Les listes sont différentes", listApprenticesTest.equals(listApprentices));
        Mockito.verify(this.apprenticeMentorRepository,times(1)).findByEmail(apprenticeMentor.getEmail());
        Mockito.verifyNoMoreInteractions(this.apprenticeMentorRepository);
    }

    @Test
    public void testFindApprenticesFailNoMentor(){
        //Init
        List<ApprenticeDetailsDto> list = new ArrayList<>();

        //Mock
        Mockito.when(
                this.apprenticeMentorRepository.findByEmail("test@email.com")
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        List<ApprenticeDetailsDto> listApprentices = this.apprenticeMentorService.findAprentices("test@email.com");

        //Verification
        Assert.assertTrue("Les listes sont différentes", listApprentices.equals(list));
        Mockito.verify(this.apprenticeMentorRepository,times(1)).findByEmail("test@email.com");
        Mockito.verifyNoMoreInteractions(this.apprenticeMentorRepository);
    }

    @Test
    public void testAddApprenticeSuccess(){
        //Init
        ApprenticeMentor apprenticeMentor = new ApprenticeMentor();
        apprenticeMentor.setEmail("amtest@email.com");

        //Mock
        Mockito.when(
                this.apprenticeMentorRepository.findByEmail(apprenticeMentor.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        this.apprenticeMentorService.addApprenticeMentor(apprenticeMentor);

        //Verification
        Mockito.verify(this.apprenticeMentorRepository, times(1)).findByEmail(apprenticeMentor.getEmail());
        Mockito.verify(this.apprenticeMentorRepository, times(1)).save(apprenticeMentor);
        Mockito.verifyNoMoreInteractions(this.apprenticeMentorRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddApprenticeFailAlreadyExisting(){
        //Init
        ApprenticeMentor apprenticeMentor = new ApprenticeMentor();
        apprenticeMentor.setEmail("amtest@email.com");

        //Mock
        Mockito.when(
                this.apprenticeMentorRepository.findByEmail(apprenticeMentor.getEmail())
        ).thenReturn(
                Optional.of(apprenticeMentor)
        );

        //Traitement
        this.apprenticeMentorService.addApprenticeMentor(apprenticeMentor);

        //Verification
        Mockito.verify(this.apprenticeMentorRepository, times(1)).findByEmail(apprenticeMentor.getEmail());
        Mockito.verifyNoMoreInteractions(this.apprenticeMentorRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddApprenticeFailSave(){
        //Init
        ApprenticeMentor apprenticeMentor = new ApprenticeMentor();
        apprenticeMentor.setEmail("amtest@email.com");

        //Mock
        Mockito.when(
                this.apprenticeMentorRepository.findByEmail(apprenticeMentor.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        Mockito.when(
                this.apprenticeMentorRepository.save(apprenticeMentor)
        ).thenThrow(
                RuntimeException.class
        );

        //Traitement
        this.apprenticeMentorService.addApprenticeMentor(apprenticeMentor);

        //Verification
        Mockito.verify(this.apprenticeMentorRepository, times(1)).findByEmail(apprenticeMentor.getEmail());
        Mockito.verify(this.apprenticeMentorRepository, times(1)).save(apprenticeMentor);
        Mockito.verifyNoMoreInteractions(this.apprenticeMentorRepository);
    }
}
