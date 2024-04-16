package com.sigl.sigl.repository;

import com.sigl.sigl.dto.EducationalTutorsDetailsDto;
import com.sigl.sigl.model.EducationalTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EducationalTutorRepository extends JpaRepository<EducationalTutor, Long> {

    Optional<EducationalTutor> findByEmail(String email);

    List<EducationalTutorsDetailsDto> findAllProjectedBy();
}
