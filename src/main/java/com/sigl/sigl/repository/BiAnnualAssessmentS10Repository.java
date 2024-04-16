package com.sigl.sigl.repository;

import com.sigl.sigl.model.BiannualAssessmentS10;
import com.sigl.sigl.model.BiannualAssessmentS5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BiAnnualAssessmentS10Repository extends JpaRepository<BiannualAssessmentS10, Long> {

  List<BiannualAssessmentS10> findAll();
  Optional<BiannualAssessmentS10> findByApprenticeId(Long apprenticeId);

}
