package com.sigl.sigl.controller;

import com.sigl.sigl.model.Company;
import com.sigl.sigl.model.Note;
import com.sigl.sigl.model.Notification;
import com.sigl.sigl.service.CompanyService;
import com.sigl.sigl.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
@RestController
@AllArgsConstructor
public class NoteController {

    @Autowired
    private NoteService noteService;
    @PostMapping("/addnote")
    public ResponseEntity<Object> addNote(@RequestBody Note note, Principal auth) {
        try {
            if(noteService.existsByTitle(note.getTitle())) {
                noteService.updateNote(note, auth.getName());
            } else {
                noteService.addNote(note, auth.getName());
            }
            return ResponseEntity.ok().body(Map.of("message", "Note créée avec succès"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>("La note existe déjà", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'apprenti", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("note/get")
    public ResponseEntity<List<Note>> getNotes(Principal auth) {
        return this.noteService.getNotes(auth.getName());
    }
}
