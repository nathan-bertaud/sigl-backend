package com.sigl.sigl.controller;

import com.sigl.sigl.dto.ChangePasswordByIdForm;
import com.sigl.sigl.dto.ChangePasswordForm;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.UserRepository;
import com.sigl.sigl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repo;

    @GetMapping("/getusers")
    public ResponseEntity<Page<User>> getUsers(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userService.getUsers(pageable);

            return ResponseEntity.ok(userPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/setpassword")
    public ResponseEntity<Void> updatePassword(Principal auth, @RequestBody ChangePasswordForm changePassForm) {
        return this.userService.setPassword(auth.getName(),changePassForm);
    }

    @PostMapping("/deleteuser/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();

        try {
            userService.deleteUserById(userId);
            response.put("status", 200);
            response.put("message", "Utilisateur supprimé avec succès");
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            response.put("status", 404);
            response.put("error", "Utilisateur non trouvé avec l'ID : " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/adduser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok("Utilisateur créé avec succès");
        } catch (RuntimeException e) {
            return new ResponseEntity<>("L'adresse e-mail existe déjà", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'utilisateur", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/idByEmail")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        Long userId = userService.getUserIdByEmail(email);
        if (userId != null) {
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   @PostMapping("/setPasswordByID/{userId}")
    public ResponseEntity<Map<String, Object>> setPasswordByID(@PathVariable Long userId,@RequestBody ChangePasswordByIdForm newPassword) {
        Map<String, Object> response = new HashMap<>();

        try {
            userService.setPasswordById(userId, newPassword.getNewPassword());
            response.put("status", 200);
            response.put("message", "MDP changé avec succès");
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            response.put("status", 404);
            response.put("error", "Utilisateur non trouvé avec l'ID : " + userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
