package com.sigl.sigl.controller;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Company;
import com.sigl.sigl.service.ApprenticeService;
import com.sigl.sigl.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class CompanyControlleur {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/addcompany")
    public ResponseEntity<Object> addCompany(@RequestBody Company company) {
        try {
            companyService.addCompany(company);
            return ResponseEntity.ok().body(Map.of("message", "Apprenti créé avec succès"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>("L'adresse e-mail existe déjà", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'apprenti", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
