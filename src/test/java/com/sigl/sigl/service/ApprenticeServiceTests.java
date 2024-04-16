package com.sigl.sigl.service;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Event;
import com.sigl.sigl.repository.ApprenticeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ApprenticeServiceTests {

    @Mock
    private ApprenticeRepository apprenticeRepository;

    @InjectMocks
    private ApprenticeService apprenticeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddApprenticeSuccess() {
        // Init
        Apprentice apprentice = createApprenticeTest();
        Mockito.when(
                this.apprenticeRepository.findByEmail(apprentice.getEmail()))
        .thenReturn(
                Optional.empty()
        );

        // Action
        this.apprenticeService.addApprentice(apprentice);

        // Verif
        Mockito.verify(this.apprenticeRepository, times(1)).findByEmail(apprentice.getEmail());
        Mockito.verify(this.apprenticeRepository, times(1)).save(apprentice);
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddApprenticeDuplicateEmail() {
        // Init
        Apprentice apprentice = createApprenticeTest();
        Mockito.when(
                apprenticeRepository.findByEmail(apprentice.getEmail())
        ).thenReturn(
                Optional.of(apprentice)
        );

        // Actions
        apprenticeService.addApprentice(apprentice);

        // Verif --> Exception
    }

    @Test(expected = RuntimeException.class)
    public void testAddApprenticeRepositoryError() {
        // Init
        Apprentice apprentice = createApprenticeTest();
        Mockito.when(
                apprenticeRepository.findByEmail(apprentice.getEmail())
        ).thenReturn(
                Optional.empty()
        );
        Mockito.doThrow(
                new RuntimeException("Repository error")
        ).when(apprenticeRepository).save(apprentice);

        // Actions
        apprenticeService.addApprentice(apprentice);

        // Verif --> Exception
    }

    @Test
    public void testGetEventsSuccess(){
        //Init
        Pageable pageable = createPageable();
        Apprentice apprentice = createApprenticeTest();

        Event event = new Event();
        event.setTitle("TestEvent");
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        apprentice.setEvents(eventList);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), eventList.size());
        List<Event> sublist = eventList.subList(start, end);

        Page<Event> pageToRetrieve = new PageImpl<>(sublist, pageable, eventList.size());

        Mockito.when(
                this.apprenticeRepository.findByEmail(apprentice.getEmail())
        ).thenReturn(
                Optional.of(apprentice)
        );

        //Traitement
        Page<Event> pageObtained = this.apprenticeService.getEvents(pageable, apprentice.getEmail());

        //Verification
        Assert.assertEquals(pageObtained,pageToRetrieve,"les pages ne sont pas les mêmes");
        Mockito.verify(this.apprenticeRepository, times(1)).findByEmail(apprentice.getEmail());
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
    }

    @Test
    public void testGetEventsFailNoApprentice(){
        //Init
        Pageable pageable = createPageable();
        Apprentice apprentice = createApprenticeTest();

        Mockito.when(
                this.apprenticeRepository.findByEmail(apprentice.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        Page<Event> pageObtained = this.apprenticeService.getEvents(pageable, apprentice.getEmail());

        //Verification
        Assert.assertEquals(pageObtained,null,"On n'a pas de résultats null");
        Mockito.verify(this.apprenticeRepository, times(1)).findByEmail(apprentice.getEmail());
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testGetEventsFailRunException(){
        //Init
        Pageable pageable = createPageable();
        Apprentice apprentice = mock(Apprentice.class);
        Optional<Apprentice> apprenticeOptional = Optional.of(apprentice);

        Mockito.when(
                apprenticeOptional.get().getEvents()
        ).thenThrow(
                RuntimeException.class
        );

        Mockito.when(
                this.apprenticeRepository.findByEmail(apprentice.getEmail())
        ).thenReturn(
                Optional.of(apprentice)
        );

        //Traitement
        Page<Event> pageObtained = this.apprenticeService.getEvents(pageable, apprentice.getEmail());

        //Verification
        Mockito.verify(this.apprenticeRepository, times(1)).findByEmail(apprentice.getEmail());
        Mockito.verifyNoMoreInteractions(this.apprenticeRepository);
    }

    public static Pageable createPageable() {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 2;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        return pageable;
    }

    /**
     * Méthode pour créer un nouvel apprenti qui va permettre les tests
     * @return L'apprenti test
     */
    private static Apprentice createApprenticeTest() {
        Apprentice apprentice = new Apprentice();
        apprentice.setCurrentYear(2023);
        apprentice.setMinorSpecialization("TestMinor");
        apprentice.setMajorSpecialization("TestMajor");
        apprentice.setEmail("test@test.com");
        apprentice.setFirstName("TestFirstName");
        apprentice.setName("TestName");
        apprentice.setPassword("TestPassword");
        return apprentice;
    }
}

