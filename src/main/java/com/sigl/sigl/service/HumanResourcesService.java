package com.sigl.sigl.service;

import com.sigl.sigl.model.HumanResources;
import com.sigl.sigl.repository.HumanResourcesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HumanResourcesService {

    @Autowired
    private HumanResourcesRepository humanResourcesRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(HumanResources.class);

    public void addHumanResources(HumanResources humanResources) {
        Optional<HumanResources> userOptional = humanResourcesRepository.findByEmail(humanResources.getEmail());

        if (userOptional.isPresent()) {
            LOGGER.error("Tentative de création d'un apprenti avec une adresse e-mail existante : {}", humanResources.getEmail());
            throw new RuntimeException("L'adresse e-mail existe déjà");
        }

        try {
            humanResourcesRepository.save(humanResources);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la création de l'apprenti : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création de l'apprenti", e);
        }
    }
}
