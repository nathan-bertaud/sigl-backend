package com.sigl.sigl.service;

import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.model.ApprenticeMentor;
import com.sigl.sigl.model.EducationalTutor;
import com.sigl.sigl.repository.EducationalTutorRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EducationalTutorService {

    @Autowired
    private final EducationalTutorRepository educationalTutorRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public void addEducationalTutor(EducationalTutor educationalTutor) {
        Optional<EducationalTutor> userOptional = educationalTutorRepository.findByEmail(educationalTutor.getEmail());

        if (userOptional.isPresent()) {
            LOGGER.error("Tentative de création d'un tuteur pédagogique avec une adresse e-mail existante : {}", educationalTutor.getEmail());
            throw new RuntimeException("L'adresse e-mail existe déjà");
        }

        try {
            educationalTutorRepository.save(educationalTutor);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la création d'un tuteur pédagogique : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création d'un tuteur pédagogique", e);
        }
    }

    public List<ApprenticeDetailsDto> findAprentices(String email) {
        Optional<EducationalTutor> educationalTutor = this.educationalTutorRepository.findByEmail(email);
        List<ApprenticeDetailsDto> list = new ArrayList<>();
        if (educationalTutor.isPresent()) {
            educationalTutor.get().getApprentices().forEach(apprentice -> {
                list.add(ApprenticeDetailsDto.fromApprentice(apprentice));
            });
        }
        return list;
    }
}