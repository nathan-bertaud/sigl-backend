package com.sigl.sigl.repository;

import com.sigl.sigl.model.BiannualAssessmentS5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BiAnnualAssessmentS5Repository extends JpaRepository<BiannualAssessmentS5, Long> {

  List<BiannualAssessmentS5> findAll();
  Optional<BiannualAssessmentS5> findByApprenticeId(Long apprenticeId);

  boolean existsByApprentice_Id(Long apprenticeId);

}
