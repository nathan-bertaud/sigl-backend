package com.sigl.sigl.repository;

import com.sigl.sigl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAll();
  Optional<User> findByEmail(String email);

  User findByNameAndFirstName(String name, String firstName);
}
