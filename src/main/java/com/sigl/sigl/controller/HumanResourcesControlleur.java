package com.sigl.sigl.controller;

import com.sigl.sigl.model.HumanResources;
import com.sigl.sigl.service.HumanResourcesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HumanResourcesControlleur {

    @Autowired
    private HumanResourcesService humanResourcesService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/addhumanresources")
    public ResponseEntity<String> addHumanResources(@RequestBody HumanResources humanResources) {
        try {
            humanResources.setPassword(passwordEncoder.encode(humanResources.getPassword()));
            humanResourcesService.addHumanResources(humanResources);
            return ResponseEntity.ok().body("{\"message\": \"Compte entreprise partenaire créé avec succès\"}");
        } catch (RuntimeException e) {
            return new ResponseEntity<>("L'adresse e-mail existe déjà", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création du compte entreprise partenaire", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
