package com.sigl.sigl.repository;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.SummarySheet;
import com.sigl.sigl.model.TechnicalAnalysis;
import com.sigl.sigl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TechnicalAnalysisRepository extends JpaRepository<TechnicalAnalysis, Long> {

  List<TechnicalAnalysis> findAll();
  boolean existsByHash(String hash);
  List<TechnicalAnalysis> findByAuthor(User user);

}
