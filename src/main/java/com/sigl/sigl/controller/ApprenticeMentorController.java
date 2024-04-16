package com.sigl.sigl.controller;

import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.dto.ApprenticeMentorsDetailsDto;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.dto.ApprenticeMentorsDetailsDto;
import com.sigl.sigl.model.ApprenticeMentor;
import com.sigl.sigl.repository.ApprenticeMentorRepository;
import com.sigl.sigl.service.ApprenticeMentorService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apprentice-mentor")
@AllArgsConstructor
public class ApprenticeMentorController {
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final ApprenticeMentorRepository repo;
  private final ApprenticeMentorService apprenticeMentorService;

  @PostMapping("/addapprenticementor")
  public ResponseEntity<Object> addApprenticeMentor(@RequestBody ApprenticeMentor apprenticeMentor) {
    try {
      apprenticeMentor.setPassword(passwordEncoder.encode(apprenticeMentor.getPassword()));
      apprenticeMentorService.addApprenticeMentor(apprenticeMentor);
      return ResponseEntity.ok().body(Map.of("message", "Maitre d'apprentissage créé avec succès"));
    } catch (RuntimeException e) {
      return new ResponseEntity<>("L'adresse e-mail existe déjà", HttpStatus.CONFLICT);
    } catch (Exception e) {
      return new ResponseEntity<>("Erreur lors de la création du maitre d'apprentissage", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/apprentices")
  public ResponseEntity<List<ApprenticeDetailsDto>> getApprentices(Principal auth) {
    List<ApprenticeDetailsDto> apprenticeList = this.apprenticeMentorService.findAprentices(auth.getName());
    return ResponseEntity.ok(apprenticeList);
  }

  @GetMapping("/getapprenticementors")
  public ResponseEntity<List<ApprenticeMentorsDetailsDto>> getAllApprenticeMentors() {
    List<ApprenticeMentorsDetailsDto> apprenticeMentors = apprenticeMentorService.findApprenticeMentorsDetails();
    return ResponseEntity.ok(apprenticeMentors);
  }

}
