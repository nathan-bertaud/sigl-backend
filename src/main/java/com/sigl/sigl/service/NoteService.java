package com.sigl.sigl.service;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Note;
import com.sigl.sigl.model.Notification;
import com.sigl.sigl.model.User;
import com.sigl.sigl.repository.NoteRepository;
import com.sigl.sigl.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    ApprenticeService apprenticeService;

    @Autowired
    private UserRepository userRepository;

    public void addNote(Note note, String email) {

        User user = userRepository.findByEmail(email).orElse(new User());
        Optional<Apprentice> optionalApprentice = apprenticeService.getApprenticeById(user.getId());
        Apprentice apprentice = optionalApprentice.orElse(null);
        //Optional<Note> noteOptional = noteRepository.findById(note.getId());
        note.setApprentice(apprentice);
        try {

            noteRepository.save(note);
        } catch (Exception e) {
            //LOGGER.error("Erreur lors de la création de l'entreprise : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création de l'apprenti", e);
        }
    }

    public void updateNote(Note note, String email) {

        User user = userRepository.findByEmail(email).orElse(new User());
        Optional<Apprentice> optionalApprentice = apprenticeService.getApprenticeById(user.getId());
        Apprentice apprentice = optionalApprentice.orElse(null);

        Optional<Note> optionalNote = noteRepository.findByTitle(note.getTitle());
        Note existingNote = optionalNote.orElseThrow(() -> new EntityNotFoundException());


        existingNote.setResume(note.getResume());
        existingNote.setStartDate(note.getStartDate());
        existingNote.setEndDate(note.getEndDate());
        existingNote.setResume(note.getResume());
        try {
            noteRepository.save(note);
        } catch (Exception e) {
            //LOGGER.error("Erreur lors de la création de l'entreprise : {}", e.getMessage());
            throw new RuntimeException("Erreur lors de la création de l'apprenti", e);
        }
    }
    public ResponseEntity<List<Note>> getNotes(String email) {
        User user = userRepository.findByEmail(email).orElse(new User());
        Optional<List<Note>> notes = this.noteRepository.findByApprenticeId(user.getId());
        if (notes.isPresent()) {
            return ResponseEntity.ok(notes.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }
    public boolean existsByTitle(String title) {
        return noteRepository.existsByTitle(title);
    }
}
