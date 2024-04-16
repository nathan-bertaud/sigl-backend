package com.sigl.sigl.service;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Company;
import com.sigl.sigl.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(Apprentice.class);

    public void addCompany(Company company) {
        Optional<Company> companyOptional = companyRepository.findByEmail(company.getEmail());

        if (companyOptional.isPresent()) {
            LOGGER.error("Tentative de création d'une entreprise avec une adresse e-mail existante : {}", company.getEmail());
            throw new RuntimeException("L'adresse e-mail existe déjà");
        }

        try {
            companyRepository.save(company);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la création de l'entreprise : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création de l'apprenti", e);
        }
    }

}

