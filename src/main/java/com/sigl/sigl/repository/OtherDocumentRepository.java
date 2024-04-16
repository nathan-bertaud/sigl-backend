package com.sigl.sigl.repository;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.OtherDocument;
import com.sigl.sigl.model.Support;
import com.sigl.sigl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OtherDocumentRepository extends JpaRepository<OtherDocument, Long> {

  List<OtherDocument> findAll();
  boolean existsByHash(String hash);
  List<OtherDocument> findByAuthor(User user);
}
