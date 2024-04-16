package com.sigl.sigl.repository;

import com.sigl.sigl.model.BiannualAssessmentS5;
import com.sigl.sigl.model.BiannualAssessmentS7;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BiAnnualAssessmentS7Repository extends JpaRepository<BiannualAssessmentS7, Long> {

  List<BiannualAssessmentS7> findAll();
  Optional<BiannualAssessmentS7> findByApprenticeId(Long apprenticeId);

}
