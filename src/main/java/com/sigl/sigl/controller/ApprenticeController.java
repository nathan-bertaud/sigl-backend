package com.sigl.sigl.controller;

import com.sigl.sigl.dto.EducationalTutorsDetailsDto;
import com.sigl.sigl.dto.EventByIdApprentice;
import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.dto.CurrentYearDto;
import com.sigl.sigl.dto.EventByIdApprentice;
import com.sigl.sigl.dto.UserNameFirstNameDto;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Event;
import com.sigl.sigl.service.ApprenticeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("apprentice")
public class ApprenticeController {

    @Autowired
    private ApprenticeService apprenticeService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<Object> addApprentice(@RequestBody Apprentice apprentice) {
        try {
            apprentice.setPassword(passwordEncoder.encode(apprentice.getPassword()));
            apprenticeService.addApprentice(apprentice);
            return ResponseEntity.ok().body(Map.of("message", "Apprenti créé avec succès"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>("L'adresse e-mail existe déjà", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'apprenti", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getevents")
    public ResponseEntity<Page<Event>> getEventsByApprentice(Principal auth,@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Event> eventPage = apprenticeService.getEvents(pageable,auth.getName());

            return ResponseEntity.ok(eventPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/getapprenticesbyyear")
    public ResponseEntity<Optional<List<ApprenticeDetailsDto>>> getApprenticesByYear(@RequestBody CurrentYearDto currentYear) {
        Optional<List<ApprenticeDetailsDto>> apprentices = apprenticeService.getApprenticesByYear(currentYear.getCurrentYear());
        return ResponseEntity.ok(apprentices);
    }

    @PostMapping("/updateapprentices")
    public ResponseEntity<Object> updateApprentices(@RequestBody List<ApprenticeDetailsDto> apprenticeModified){
        try{
            for(ApprenticeDetailsDto apprentice : apprenticeModified){
                apprenticeService.updateApprentice(apprentice);
            }
            return ResponseEntity.ok().body(Map.of("message", "Mise à jour réussi"));
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @GetMapping("/getapprenticebyId/{userId}")
    public ResponseEntity<Optional<Apprentice>> getApprenticeById(@PathVariable long userId) {
        try {
            Optional<Apprentice> apprentice = apprenticeService.getApprenticeById(userId);
            return ResponseEntity.ok(apprentice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getapprentices")
    public ResponseEntity<List<UserNameFirstNameDto>> getApprentices() {
        List<UserNameFirstNameDto> apprenticeList = this.apprenticeService.findApprenticeDetails();
        return ResponseEntity.ok(apprenticeList);
    }
}
