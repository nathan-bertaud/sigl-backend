package com.sigl.sigl.repository;

import com.sigl.sigl.dto.ApprenticeMentorsDetailsDto;
import com.sigl.sigl.model.ApprenticeMentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sigl.sigl.dto.ApprenticeMentorsDetailsDto;
import com.sigl.sigl.model.ApprenticeMentor;

@RepositoryRestResource
public interface ApprenticeMentorRepository extends JpaRepository<ApprenticeMentor, Long> {
  Optional<ApprenticeMentor> findByEmail(String email);

  List<ApprenticeMentorsDetailsDto> findAllProjectedBy();
}
