package com.sigl.sigl.service;

import com.sigl.sigl.dto.EventForm;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Coordinator;
import com.sigl.sigl.model.Event;
import com.sigl.sigl.model.Semestre;
import com.sigl.sigl.repository.AdministratorRepository;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.CoordinatorRepository;
import com.sigl.sigl.repository.EventRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CoordinatorRepository coordinatorRepository;

    @Mock
    private ApprenticeRepository apprenticeRepository;

    @Mock
    private AdministratorRepository administratorRepository;

    @InjectMocks
    private EventService eventService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddEventSuccessCoordinator(){
        //Init
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("test@email.com");
        Date now = new Date();

        EventForm eventForm = new EventForm();
        eventForm.setSemestre(Semestre.S10);
        eventForm.setStartDate(now);
        eventForm.setEndDate(now);
        eventForm.setTitle("TestEvent");

        Apprentice apprentice1 = new Apprentice();
        apprentice1.setEmail("apprentice1@email.com");
        List<Apprentice> apprenticesList = new ArrayList<>();
        apprenticesList.add(apprentice1);

        Event event1 = new Event();
        event1.setStartDate(eventForm.getStartDate());
        event1.setEndDate(eventForm.getEndDate());
        event1.setPlace(null);
        event1.setTitle(eventForm.getTitle());
        event1.setSemestre(eventForm.getSemestre());
        event1.setApprentice(apprentice1);
        List<Event> eventList = new ArrayList<>();
        eventList.add(event1);

        Optional<List<Apprentice>> apprenticesOptional = Optional.of(apprenticesList);

        Mockito.when(
                coordinatorRepository.findByEmail(coordinator.getEmail())
        ).thenReturn(
                Optional.of(coordinator)
        );

        Mockito.when(
                administratorRepository.findByEmail(coordinator.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        Mockito.when(
                apprenticeRepository.findByCurrentYear(5)
        ).thenReturn(
                apprenticesOptional
        );

        //Traitement
        eventService.addEvent(coordinator.getEmail(), eventForm);

        //Verifications
        Mockito.verify(this.apprenticeRepository, times(1)).findByCurrentYear(5);
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
        Mockito.verify(this.coordinatorRepository, times(1)).findByEmail(coordinator.getEmail());
        Mockito.verifyNoMoreInteractions(this.coordinatorRepository);
        Mockito.verify(this.administratorRepository, times(1)).findByEmail(coordinator.getEmail());
        Mockito.verifyNoMoreInteractions(this.administratorRepository);
        Mockito.verify(this.eventRepository, times(1)).saveAll(eventList);
        Mockito.verifyNoMoreInteractions(this.eventRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddEventFailNoEmail(){
        //Init
        EventForm eventForm = new EventForm();

        Mockito.when(
                coordinatorRepository.findByEmail("email@test.com")
        ).thenReturn(
                Optional.empty()
        );

        Mockito.when(
                administratorRepository.findByEmail("email@test.com")
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        eventService.addEvent("email@test.com", eventForm);

        //Verifications
        Mockito.verify(this.coordinatorRepository, times(1)).findByEmail("email@test.com");
        Mockito.verifyNoMoreInteractions(this.coordinatorRepository);
        Mockito.verify(this.administratorRepository, times(1)).findByEmail("email@test.com");
        Mockito.verifyNoMoreInteractions(this.administratorRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddEventFailNoSemester(){
        //Init
        Coordinator coordinator = new Coordinator();
        coordinator.setEmail("test@email.com");
        Date now = new Date();

        EventForm eventForm = new EventForm();
        eventForm.setStartDate(now);
        eventForm.setEndDate(now);
        eventForm.setTitle("TestEvent");

        Mockito.when(
                coordinatorRepository.findByEmail(coordinator.getEmail())
        ).thenReturn(
                Optional.of(coordinator)
        );

        Mockito.when(
                administratorRepository.findByEmail(coordinator.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        eventService.addEvent(coordinator.getEmail(), eventForm);

        //Verifications
        Mockito.verify(this.coordinatorRepository, times(1)).findByEmail(coordinator.getEmail());
        Mockito.verifyNoMoreInteractions(this.coordinatorRepository);
        Mockito.verify(this.administratorRepository, times(1)).findByEmail(coordinator.getEmail());
        Mockito.verifyNoMoreInteractions(this.administratorRepository);
    }
}
