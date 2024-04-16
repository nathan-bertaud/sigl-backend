package com.sigl.sigl.repository;

import com.sigl.sigl.model.BiannualAssessmentS5;
import com.sigl.sigl.model.BiannualAssessmentS9;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BiAnnualAssessmentS9Repository extends JpaRepository<BiannualAssessmentS9, Long> {

  List<BiannualAssessmentS9> findAll();
  Optional<BiannualAssessmentS9> findByApprenticeId(Long apprenticeId);

}
