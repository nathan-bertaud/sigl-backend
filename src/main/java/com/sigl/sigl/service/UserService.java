package com.sigl.sigl.service;

import com.sigl.sigl.dto.ChangePasswordForm;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.UserRepository;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public void deleteUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.deleteById(userId);
        }
        else {
            throw new RuntimeException("L'utilisateur avec l'id" + userId + " n'existe pas");
        }
    }


    public Long getUserIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get().getId();
        }else{
            throw new RuntimeException("L'utilisateur avec l'email" + email + " n'existe pas");
        }
    }

    public void addUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            LOGGER.error("Tentative de création d'un utilisateur avec une adresse e-mail existante : {}", user.getEmail());
            throw new RuntimeException("L'adresse e-mail existe déjà");
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la création de l'utilisateur : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    public Page<User> getUsers(Pageable pageable) {
        try {
            return this.userRepository.findAll(pageable);
        } catch (RuntimeException e) {
        throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
        }
    }

    public ResponseEntity<Void> setPassword(String email, ChangePasswordForm changePassForm) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(changePassForm.getPassword(), user.getPassword())){//.equals(passwordEncoder.encode(changePassForm.getPassword())))) {
                user.setPassword(passwordEncoder.encode(changePassForm.getNewPassword()));
                userRepository.save(user);
                return ResponseEntity.noContent().build();
            } else {
                throw new RuntimeException();
            }

        } else {
           throw new NoResultException();
        }
    }

    public void updateLastConnexionUser(User user){
        if(user != null) {
            Date dateNow = new Date();
            user.setLastConnexionDate(dateNow);
            userRepository.save(user);
        }else{
            throw new NoResultException("L'utilisateur avec l'id n'existe pas");
        }
    }

    public void setPasswordById(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("L'utilisateur avec l'id" + userId + " n'existe pas");
        }
    }
}
