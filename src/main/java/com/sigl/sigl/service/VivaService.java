package com.sigl.sigl.service;

import com.sigl.sigl.dto.UserNameFirstNameDto;
import com.sigl.sigl.dto.VivaDto;
import com.sigl.sigl.dto.VivaEditDto;
import com.sigl.sigl.model.*;
import com.sigl.sigl.repository.ApprenticeRepository;
import com.sigl.sigl.repository.CoordinatorRepository;
import com.sigl.sigl.repository.UserRepository;
import com.sigl.sigl.repository.VivaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VivaService {

    @Autowired
    private CoordinatorRepository coordinatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VivaRepository vivaRepository;

    @Autowired
    private ApprenticeRepository apprenticeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    public void addJuries(String email, List<UserNameFirstNameDto> juries){
        Coordinator coordinator = coordinatorRepository.findByEmail(email).orElse(null);

        if (coordinator==null) {
            LOGGER.error("Tentative de création d'un juries avec un email coordinator qui n'existe pas : {}", email);
            throw new RuntimeException("L'email ne correspond pas à un coordinateur");
        }

        if(juries.size()>=3){
            List<User> userlist = new ArrayList<>();
            for(UserNameFirstNameDto user : juries){
                User findUser = userRepository.findByNameAndFirstName(user.getName(), user.getFirstName());
                userlist.add(findUser);
            }
            Viva viva = new Viva();
            viva.setJuries(userlist);
            vivaRepository.save(viva);
        }else{
            throw new RuntimeException("Il manque des juries");
        }
    }

    public Page<VivaDto> findAllJuries(Pageable pageable) {
        try {
            Page<Viva> vivas = this.vivaRepository.findAll(pageable);
            Page<VivaDto> listVivas = vivas.map(viva -> {
                List<UserNameFirstNameDto> listJuries = viva.getJuries().stream()
                        .map(user -> new UserNameFirstNameDto(user.getId(),user.getName(), user.getFirstName()))
                        .collect(Collectors.toList());

                return new VivaDto(viva.getId(), listJuries,viva.getApprentice(),viva.getTitle(),viva.getStartDate(),viva.getEndDate()
                        ,viva.getPlace(),viva.getSemestre());
            });
            return listVivas;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
        }

    }

    public void editViva(String email, VivaEditDto vivas) {
        Coordinator coordinator = coordinatorRepository.findByEmail(email).orElse(null);
        Viva viva = vivaRepository.findById(vivas.getId()).orElse(null);
        Apprentice apprentice = apprenticeRepository.findById(vivas.getIdApprentice()).orElse(null);
        if (coordinator==null){
            LOGGER.error("Tentative de création d'un viva avec un email coordinator qui n'existe pas : {}", email);
            throw new RuntimeException("L'email ne correspond pas à un coordinateur");
        }
        if (viva==null){
            LOGGER.error("Tentative de création d'un viva avec un id viva qui n'existe pas : {}", vivas.getId());
            throw new RuntimeException("L'id ne correspond pas à un viva");
        }
        if (apprentice==null){
            LOGGER.error("Tentative de création d'un viva avec un id apprentice qui n'existe pas : {}", vivas.getIdApprentice());
            throw new RuntimeException("L'id ne correspond pas à un apprentice");
        }

        // Validate Semestre
        if (!isValidSemestre(vivas.getSemestre())) {
            throw new RuntimeException("Le semestre n'est pas valide");
        }

        viva.setTitle(vivas.getTitle());
        viva.setApprentice(apprentice);
        viva.setPlace(vivas.getPlace());
        viva.setSemestre(vivas.getSemestre());
        viva.setStartDate(vivas.getStartDate());
        viva.setEndDate(vivas.getEndDate());
        vivaRepository.save(viva);
    }

    // Validation method for Semestre
    private boolean isValidSemestre(Semestre semestre) {
        return EnumSet.of(Semestre.S5, Semestre.S6, Semestre.S7, Semestre.S8, Semestre.S9, Semestre.S10).contains(semestre);
    }
}
