package com.sigl.sigl.controller;

import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.dto.ApprenticeMentorsDetailsDto;
import com.sigl.sigl.dto.EducationalTutorsDetailsDto;
import com.sigl.sigl.dto.UserDetailsDto;
import com.sigl.sigl.model.ApprenticeMentor;
import com.sigl.sigl.model.EducationalTutor;
import com.sigl.sigl.repository.ApprenticeMentorRepository;
import com.sigl.sigl.repository.EducationalTutorRepository;
import com.sigl.sigl.service.ApprenticeMentorService;
import com.sigl.sigl.service.EducationalTutorService;
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
@AllArgsConstructor
@RequestMapping("/apprentice-tutor")
public class EducationalTutorController {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final EducationalTutorRepository repo;
    private final EducationalTutorService educationalTutorService;

    @PostMapping("/addeducationaltutor")
    public ResponseEntity<Object> addEducationalTutor(@RequestBody EducationalTutor educationalTutor) {
        try {
            educationalTutor.setPassword(passwordEncoder.encode(educationalTutor.getPassword()));
            educationalTutorService.addEducationalTutor(educationalTutor);
            return ResponseEntity.ok().body(Map.of("message", "Tuteur pédagogique créé avec succès"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>("L'adresse e-mail existe déjà", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création du tuteur pédagogique", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/geteducationaltutor")
    public ResponseEntity<List<EducationalTutorsDetailsDto>> EducationalTutors() {
        List<EducationalTutorsDetailsDto> educationalTutorsDetails = repo.findAllProjectedBy();
        return ResponseEntity.ok(educationalTutorsDetails);
    }

    @GetMapping("/getapprentices")
    public ResponseEntity<List<ApprenticeDetailsDto>> getApprentices(Principal auth) {
        List<ApprenticeDetailsDto> apprenticeList = this.educationalTutorService.findAprentices(auth.getName());
        return ResponseEntity.ok(apprenticeList);
    }
}
