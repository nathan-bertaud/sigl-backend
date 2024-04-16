package com.sigl.sigl.repository;
import com.sigl.sigl.model.HumanResources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface HumanResourcesRepository extends JpaRepository<HumanResources, Long> {

    Optional<HumanResources> findByEmail(String email);

}
