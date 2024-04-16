package com.sigl.sigl.service;

import com.sigl.sigl.dto.BiannualMeetingForm;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.BiannualMeeting;
import com.sigl.sigl.model.Semestre;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.BiannualMeetingRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.times;

public class BiannualMeetingServiceTest {

    @Mock
    private BiannualMeetingRepository biannualMeetingRepository;

    @Mock
    private ApprenticeRepository apprenticeRepository;

    @InjectMocks
    private BiannualMeetingService biannualMeetingService;

    @Before
    public void setUp() { MockitoAnnotations.initMocks(this); }

    @Test
    public void testAddBiannualMeetingSuccess(){
        //Init
        Apprentice apprenticeTest = new Apprentice();
        apprenticeTest.setEmail("test@email.com");
        apprenticeTest.setName("Test");
        Date now = new Date();
        BiannualMeetingForm biannualMeetingForm = new BiannualMeetingForm();
        biannualMeetingForm.setStartDate(now);
        biannualMeetingForm.setEndDate(now);
        biannualMeetingForm.setPlace("ESEO");
        biannualMeetingForm.setSemestre(Semestre.S10);
        BiannualMeeting biannualMeeting = new BiannualMeeting();
        biannualMeeting.setEndDate(biannualMeetingForm.getEndDate());
        biannualMeeting.setStartDate(biannualMeetingForm.getStartDate());
        biannualMeeting.setApprentice(apprenticeTest);
        biannualMeeting.setSemestre(biannualMeetingForm.getSemestre());
        biannualMeeting.setPlace(biannualMeetingForm.getPlace());

        //Mock
        Mockito.when(
                this.apprenticeRepository.findByEmail(apprenticeTest.getEmail())
        ).thenReturn(
                Optional.of(apprenticeTest)
        );

        //Traitement
        this.biannualMeetingService.addBiannualMeeting(apprenticeTest.getEmail(),biannualMeetingForm);

        //Verification
        Mockito.verify(this.apprenticeRepository, times(1)).findByEmail(apprenticeTest.getEmail());
        Mockito.verify(this.biannualMeetingRepository, times(1)).save(biannualMeeting);
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
        Mockito.verifyNoMoreInteractions(this.biannualMeetingRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddBiannualMeetingFailNonValidSemester(){
        //Init
        Apprentice apprenticeTest = new Apprentice();
        apprenticeTest.setEmail("test@email.com");
        apprenticeTest.setName("Test");
        Date now = new Date();
        BiannualMeetingForm biannualMeetingForm = new BiannualMeetingForm();
        biannualMeetingForm.setStartDate(now);
        biannualMeetingForm.setEndDate(now);
        biannualMeetingForm.setPlace("ESEO");
        biannualMeetingForm.setSemestre(null);

        //Mock
        Mockito.when(
                this.apprenticeRepository.findByEmail(apprenticeTest.getEmail())
        ).thenReturn(
                Optional.of(apprenticeTest)
        );

        //Traitement
        this.biannualMeetingService.addBiannualMeeting(apprenticeTest.getEmail(),biannualMeetingForm);

        //Verification
        Mockito.verify(this.apprenticeRepository, times(1)).findByEmail("test@email.com");
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
        Mockito.verifyNoMoreInteractions(this.biannualMeetingRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddBiannualMeetingFailApprenticeNull(){

        //Mock
        Mockito.when(
                this.apprenticeRepository.findByEmail("test@email.com")
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        this.biannualMeetingService.addBiannualMeeting("test@email.com",null);

        //Verification
        Mockito.verify(this.apprenticeRepository, times(1)).findByEmail("test@email.com");
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
        Mockito.verifyNoMoreInteractions(this.biannualMeetingRepository);
    }
}
