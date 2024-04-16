package com.sigl.sigl.controller;
import com.sigl.sigl.dto.BiannualMeetingForm;
import com.sigl.sigl.model.BiannualMeeting;
import com.sigl.sigl.repository.BiannualMeetingRepository;
import com.sigl.sigl.service.BiannualMeetingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("meeting")
@Transactional
public class BiannualMeetingController {
    @Autowired
    private BiannualMeetingRepository repo;
    @Autowired
    private BiannualMeetingService biannualMeetingService;

    @GetMapping("getall")
    public ResponseEntity<List<BiannualMeeting>> getBiannualMeeting() {
        List<BiannualMeeting> biannualMeeting = repo.findAll();
        return ResponseEntity.ok(biannualMeeting);
    }
    @PostMapping("add")
    public ResponseEntity<String> addBiannualMeeting(Principal auth, @RequestBody BiannualMeetingForm biannualMeetingForm) {
        try {
            biannualMeetingService.addBiannualMeeting(auth.getName(),biannualMeetingForm);
            return ResponseEntity.ok("BiannualMeeting créé avec succès");
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création d'un BiannualMeeting", HttpStatus.BAD_REQUEST);
        }
    }


}
