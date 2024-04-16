package com.sigl.sigl.repository;

import com.sigl.sigl.model.Viva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VivaRepository extends JpaRepository<Viva, Long> {
}
