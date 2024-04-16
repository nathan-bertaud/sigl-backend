package com.sigl.sigl.repository;

import com.sigl.sigl.model.BiannualAssessmentS5;
import com.sigl.sigl.model.BiannualAssessmentS6;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BiAnnualAssessmentS6Repository extends JpaRepository<BiannualAssessmentS6, Long> {

  List<BiannualAssessmentS6> findAll();
  Optional<BiannualAssessmentS6> findByApprenticeId(Long apprenticeId);

}
