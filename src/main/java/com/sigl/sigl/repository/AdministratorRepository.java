package com.sigl.sigl.repository;

import com.sigl.sigl.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Optional<Administrator> findByEmail(String email);
}
