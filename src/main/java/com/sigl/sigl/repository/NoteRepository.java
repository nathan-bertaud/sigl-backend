package com.sigl.sigl.repository;

import com.sigl.sigl.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional <List<Note>> findByApprenticeId(Long apprenticeId);

    boolean existsByTitle(String title);

    Optional<Note> findByTitle(String title);
}
