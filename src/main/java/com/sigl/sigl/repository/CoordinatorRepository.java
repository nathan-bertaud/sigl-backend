package com.sigl.sigl.repository;

import com.sigl.sigl.model.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {

    Optional<Coordinator> findByEmail(String email);

}
