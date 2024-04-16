package com.sigl.sigl.service;

import com.sigl.sigl.dto.EventForm;
import com.sigl.sigl.model.*;
import com.sigl.sigl.repository.AdministratorRepository;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.CoordinatorRepository;
import com.sigl.sigl.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ApprenticeRepository apprenticeRepository;

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    public void addEvent(String email, EventForm eventForm){
        Coordinator coordinator = coordinatorRepository.findByEmail(email).orElse(null);
        Administrator admin = administratorRepository.findByEmail(email).orElse(null);

        if (coordinator==null && admin==null) {
            LOGGER.error("Tentative de création d'un event avec un email coordinator/administrator qui n'existe pas : {}", email);
            throw new RuntimeException("L'email ne correspond ni à un coordinateur ni à un administrateur");
        }

        // Validate Semestre
        if (!isValidSemestre(eventForm.getSemestre())) {
            throw new RuntimeException("Le semestre n'est pas valide");
        }

        Optional<List<Apprentice>> apprenticesOptional = switch (eventForm.getSemestre()) {
            case S5, S6 -> apprenticeRepository.findByCurrentYear(3);
            case S7, S8 -> apprenticeRepository.findByCurrentYear(4);
            case S9, S10 -> apprenticeRepository.findByCurrentYear(5);
        };

        List<Event> eventList = new ArrayList<>();


        if(apprenticesOptional .isPresent()){
            List<Apprentice> apprentices = apprenticesOptional.get();
            for (Apprentice apprenti : apprentices ) {
                Event event = new Event();
                event.setStartDate(eventForm.getStartDate());
                event.setEndDate(eventForm.getEndDate());
                event.setPlace(null);
                event.setTitle(eventForm.getTitle());
                event.setSemestre(eventForm.getSemestre());
                event.setApprentice(apprenti);
                eventList.add(event);
            }
            eventRepository.saveAll(eventList);
        }


    }

    // Validation method for Semestre
    private boolean isValidSemestre(Semestre semestre) {
        return EnumSet.of(Semestre.S5, Semestre.S6, Semestre.S7, Semestre.S8, Semestre.S9, Semestre.S10).contains(semestre);
    }

}
