package com.sigl.sigl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.dto.ApprenticeMentorsDetailsDto;
import com.sigl.sigl.model.ApprenticeMentor;
import com.sigl.sigl.repository.ApprenticeMentorRepository;

@Service
public class ApprenticeMentorService {

  @Autowired
  private ApprenticeMentorRepository apprenticeMentorRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  public List<ApprenticeDetailsDto> findAprentices(String email) {
    Optional<ApprenticeMentor> apprenticeMentor = this.apprenticeMentorRepository.findByEmail(email);
    List<ApprenticeDetailsDto> list = new ArrayList<>();
    if (apprenticeMentor.isPresent()) {
      apprenticeMentor.get().getApprentices().forEach(apprentice -> {
        list.add(ApprenticeDetailsDto.fromApprentice(apprentice));
      });
    }
    return list;
  }

  public List<ApprenticeMentorsDetailsDto> findApprenticeMentorsDetails() {
    return apprenticeMentorRepository.findAllProjectedBy();
  }

  public void addApprenticeMentor(ApprenticeMentor apprenticeMentor) {
    Optional<ApprenticeMentor> userOptional = apprenticeMentorRepository.findByEmail(apprenticeMentor.getEmail());

    if (userOptional.isPresent()) {
      LOGGER.error("Tentative de création d'un apprenti avec une adresse e-mail existante : {}", apprenticeMentor.getEmail());
      throw new RuntimeException("L'adresse e-mail existe déjà");
    }

    try {
      apprenticeMentorRepository.save(apprenticeMentor);
    } catch (Exception e) {
      LOGGER.error("Erreur lors de la création de l'apprenti : {}", e.getMessage());
      throw new RuntimeException("Erreur lors de la création de l'apprenti", e);
    }
  }
}
