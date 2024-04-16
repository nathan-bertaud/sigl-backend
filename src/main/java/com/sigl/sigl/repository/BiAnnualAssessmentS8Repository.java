package com.sigl.sigl.repository;

import com.sigl.sigl.model.BiannualAssessmentS5;
import com.sigl.sigl.model.BiannualAssessmentS8;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BiAnnualAssessmentS8Repository extends JpaRepository<BiannualAssessmentS8, Long> {

  List<BiannualAssessmentS8> findAll();
  Optional<BiannualAssessmentS8> findByApprenticeId(Long apprenticeId);

}
