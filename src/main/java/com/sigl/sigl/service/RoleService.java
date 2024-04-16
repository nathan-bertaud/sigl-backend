package com.sigl.sigl.service;

import com.sigl.sigl.model.*;
import com.sigl.sigl.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

  @Autowired
  private ApprenticeRepository apprenticeRepository;

  @Autowired
  private HumanResourcesRepository humanResourcesRepository;

  @Autowired
  private ApprenticeMentorRepository apprenticeMentorRepository;

  @Autowired
  private AdministratorRepository administratorRepository;

  @Autowired
  private CoordinatorRepository coordinatorRepository;

  @Autowired
  private EducationalTutorRepository educationalTutorRepository;


  protected Set<GrantedAuthority> determineUserRoles(Long id) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    Optional<Apprentice> apprenticeOptional = apprenticeRepository.findById(id);
    if (apprenticeOptional.isPresent()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_APPRENTI"));
    }
    Optional<HumanResources> humanResourcesOptional = humanResourcesRepository.findById(id);
    if (humanResourcesOptional.isPresent()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_HR"));
    }
    Optional<Administrator> administratorOptional = administratorRepository.findById(id);
    if (administratorOptional.isPresent()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    Optional<ApprenticeMentor> apprenticeMentor = apprenticeMentorRepository.findById(id);
    if (apprenticeMentor.isPresent()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_MENTOR"));
    }
    Optional<Coordinator> coordinator = coordinatorRepository.findById(id);
    if (coordinator.isPresent()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_COORDINATOR"));
    }
    Optional<EducationalTutor> educationalTutor = educationalTutorRepository.findById(id);
    if (educationalTutor.isPresent()) {
      authorities.add(new SimpleGrantedAuthority("ROLE_TUTOR"));
    }
    return authorities;
  }
}
