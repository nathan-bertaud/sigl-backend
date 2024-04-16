package com.sigl.sigl.service;

import com.sigl.sigl.dto.UserNameFirstNameDto;
import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Event;
import com.sigl.sigl.model.Event;
import com.sigl.sigl.model.User;
import com.sigl.sigl.model.ApprenticeMentor;
import com.sigl.sigl.model.EducationalTutor;
import com.sigl.sigl.repository.ApprenticeMentorRepository;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.EducationalTutorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApprenticeService {

    @Autowired
    private ApprenticeRepository apprenticeRepository;

    @Autowired
    private EducationalTutorRepository educationalTutorRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(Apprentice.class);


    public void addApprentice(Apprentice apprentice) {
        Optional<Apprentice> userOptional = apprenticeRepository.findByEmail(apprentice.getEmail());

        if (userOptional.isPresent()) {
            LOGGER.error("Tentative de création d'un apprenti avec une adresse e-mail existante : {}", apprentice.getEmail());
            throw new RuntimeException("L'adresse e-mail existe déjà");
        }

        try {
            apprenticeRepository.save(apprentice);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la création de l'apprenti : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création de l'apprenti", e);
        }
    }

    public Optional<List<ApprenticeDetailsDto>> getApprenticesByYear(int currentYear) {
        Optional<List<Apprentice>> apprentices = apprenticeRepository.findByCurrentYear(currentYear);

        if (apprentices.isPresent()) {
            List<ApprenticeDetailsDto> apprenticeDetailsDTOList = apprentices.get() // Unwrap the List from Optional
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return Optional.of(apprenticeDetailsDTOList);
        } else {
            return Optional.empty();
        }
    }

    private ApprenticeDetailsDto convertToDto(Apprentice apprentice) {
        return new ApprenticeDetailsDto(
                apprentice.getId(),
                apprentice.getName(),
                apprentice.getFirstName(),
                apprentice.getEmail(),
                apprentice.getEducationalTutor()
        );
    }

    public void updateApprentice(ApprenticeDetailsDto apprentice) {
        Apprentice existingApprentice = apprenticeRepository.findByEmail(apprentice.getEmail()).orElse(null);
        if (existingApprentice != null) {
            educationalTutorRepository.findByEmail(apprentice.getEducationalTutor().getEmail()).ifPresent(existingApprentice::setEducationalTutor);
            apprenticeRepository.save(existingApprentice);
        }
    }

    public Page<Event> getEvents(Pageable pageable, String email) {
        Optional<Apprentice> apprenti = this.apprenticeRepository.findByEmail(email);
        if (apprenti.isPresent()) {
            try {
                List<Event> events = apprenti.get().getEvents();

                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), events.size());
                List<Event> sublist = events.subList(start, end);

                Page<Event> page = new PageImpl<>(sublist, pageable, events.size());

                return page;
            } catch (RuntimeException e) {
                throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
            }
        }else{
            return null;
        }
    }

    public Optional<Apprentice> getApprenticeById(Long id) {
        return apprenticeRepository.findById(id);
    }

    public List<UserNameFirstNameDto> findApprenticeDetails() {
        List<UserNameFirstNameDto> apprentis = this.apprenticeRepository.findAllProjectedBy();
        return apprentis;
    }
}
