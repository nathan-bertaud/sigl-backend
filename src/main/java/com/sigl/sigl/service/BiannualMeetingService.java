package com.sigl.sigl.service;

import com.sigl.sigl.dto.BiannualMeetingForm;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Semestre;
import com.sigl.sigl.model.BiannualMeeting;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.BiannualMeetingRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@Transactional
public class BiannualMeetingService {
    @Autowired
    private BiannualMeetingRepository biannualMeetingRepository;

    @Autowired
    private ApprenticeRepository apprenticeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BiannualMeetingService.class);




    public void addBiannualMeeting(String email,BiannualMeetingForm biannualMeetingForm){
        Apprentice apprentice = apprenticeRepository.findByEmail(email).orElse(null);

        if (apprentice==null) {
            LOGGER.error("Tentative de création d'un biannualMeeting avec un email apprenti qui n'existe pas : {}", email);
            throw new RuntimeException("L'email de l'apprenti n'existe pas");
        }

        // Validate Semestre
        if (!isValidSemestre(biannualMeetingForm.getSemestre())) {
            throw new RuntimeException("Le semestre n'est pas valide");
        }
        BiannualMeeting biannualMeeting = new BiannualMeeting();
        biannualMeeting.setApprentice(apprentice);
        biannualMeeting.setStartDate(biannualMeetingForm.getStartDate());
        biannualMeeting.setEndDate(biannualMeetingForm.getEndDate());
        biannualMeeting.setPlace(biannualMeetingForm.getPlace());
        biannualMeeting.setSemestre(biannualMeetingForm.getSemestre());
        biannualMeeting.setTitle(biannualMeetingForm.getTitle());
        biannualMeetingRepository.save(biannualMeeting);
    }

    // Validation method for Semestre
    private boolean isValidSemestre(Semestre semestre) {
        return EnumSet.of(Semestre.S5, Semestre.S6, Semestre.S7, Semestre.S8, Semestre.S9, Semestre.S10).contains(semestre);
    }
}
