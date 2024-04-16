package com.sigl.sigl.controller;

import com.sigl.sigl.model.*;
import com.sigl.sigl.repository.UserRepository;
import com.sigl.sigl.service.BiannualAssesmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class BiAnnualAssessmentController {
    @Autowired
    private BiannualAssesmentService biannualAssesmentService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getBiAnnualAssessmentForTutor/{idApprenti}")
    public ResponseEntity<?> getBiAnnualAssessmentForTutor(@PathVariable Long idApprenti) {
        try {
            BiannualAssessmentRequest biannualAssessmentRequest = this.biannualAssesmentService.getBiannualAssessmentRequest(idApprenti);
            return ResponseEntity.ok(biannualAssessmentRequest);
        } catch (Exception e) {
            return new ResponseEntity<>("BiAnnualAssessment Erreur GET", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getBiAnnualAssessmentForApprentice")
    public ResponseEntity<?> getBiAnnualAssessmentForApprentice(Principal auth) {
        try {
            User user = userRepository.findByEmail(auth.getName()).orElse(new User());
            BiannualAssessmentRequest biannualAssessmentRequest = this.biannualAssesmentService.getBiannualAssessmentRequest(user.getId());
            return ResponseEntity.ok(biannualAssessmentRequest);
        } catch (Exception e) {
            return new ResponseEntity<>("BiAnnualAssessment Erreur GET", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addBiAnnualAssessment/{idApprenti}")
    public ResponseEntity<Object> addBiAnnualAssessment(@PathVariable Long idApprenti, @RequestBody BiannualAssessmentRequest biannualAssessmentRequest) {

        try {
            if (biannualAssesmentService.existsByApprentice_Id(idApprenti)) {
                // Mettez à jour la grille d'évaluation existante
                this.biannualAssesmentService.updateBiannualAssessmentS5(biannualAssessmentRequest.getBiannualAssessmentS5(), idApprenti);
                this.biannualAssesmentService.updateBiannualAssessmentS6(biannualAssessmentRequest.getBiannualAssessmentS6(), idApprenti);
                this.biannualAssesmentService.updateBiannualAssessmentS7(biannualAssessmentRequest.getBiannualAssessmentS7(), idApprenti);
                this.biannualAssesmentService.updateBiannualAssessmentS8(biannualAssessmentRequest.getBiannualAssessmentS8(), idApprenti);
                this.biannualAssesmentService.updateBiannualAssessmentS9(biannualAssessmentRequest.getBiannualAssessmentS9(), idApprenti);
                this.biannualAssesmentService.updateBiannualAssessmentS10(biannualAssessmentRequest.getBiannualAssessmentS10(), idApprenti);
            } else {
                // Ajouter une nouvelle grille d'évaluation
                this.biannualAssesmentService.addBiannualAssesmentS5(biannualAssessmentRequest.getBiannualAssessmentS5(), idApprenti);
                this.biannualAssesmentService.addBiannualAssesmentS6(biannualAssessmentRequest.getBiannualAssessmentS6(), idApprenti);
                this.biannualAssesmentService.addBiannualAssesmentS7(biannualAssessmentRequest.getBiannualAssessmentS7(), idApprenti);
                this.biannualAssesmentService.addBiannualAssesmentS8(biannualAssessmentRequest.getBiannualAssessmentS8(), idApprenti);
                this.biannualAssesmentService.addBiannualAssesmentS9(biannualAssessmentRequest.getBiannualAssessmentS9(), idApprenti);
                this.biannualAssesmentService.addBiannualAssesmentS10(biannualAssessmentRequest.getBiannualAssessmentS10(), idApprenti);
            }

            return ResponseEntity.ok("Ajout ou mise à jour des évaluations réussi!");

        } catch (Exception e) {
            return new ResponseEntity<>("Erreur d'ajout ou mise à jour des évaluations", HttpStatus.BAD_REQUEST);
        }
    }


}
