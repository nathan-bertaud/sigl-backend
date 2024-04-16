package com.sigl.sigl.repository;

import com.sigl.sigl.dto.UserNameFirstNameDto;
import com.sigl.sigl.dto.ApprenticeDetailsDto;
import com.sigl.sigl.model.Apprentice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ApprenticeRepository extends JpaRepository<Apprentice, Long> {

    Optional<Apprentice> findById(Long id);
    Optional<Apprentice> findByEmail(String email);

    Optional<List<Apprentice>> findByApprenticeMentor_Email(String id);

    Optional<List<Apprentice>> findByCurrentYear(int currentYear);
    List<UserNameFirstNameDto> findAllProjectedBy();


}
