package com.sigl.sigl.controller;

import com.sigl.sigl.dto.EventForm;
import com.sigl.sigl.model.Event;
import com.sigl.sigl.repository.EventRepository;
import com.sigl.sigl.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventRepository repo;

    @Autowired
    private EventService service;

    @GetMapping("getall")
    public ResponseEntity<List<Event>> getEvent() {
        List<Event> event = repo.findAll();
        return ResponseEntity.ok(event);
    }

    @PostMapping("add")
    public ResponseEntity<String> addEvent(Principal auth, @RequestBody EventForm eventForm) {
        try {
            service.addEvent(auth.getName(), eventForm);
            return ResponseEntity.ok("keyDate créé avec succès");
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création d'un keyDate", HttpStatus.BAD_REQUEST);
        }
    }

}
