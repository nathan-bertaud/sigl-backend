package com.sigl.sigl.repository;


import com.sigl.sigl.model.SummarySheet;

import com.sigl.sigl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SummarySheetRepository extends JpaRepository<SummarySheet, Long> {

  List<SummarySheet> findAll();
  List<SummarySheet> findByAuthor(User user);
  boolean existsByHash(String hash);
}
