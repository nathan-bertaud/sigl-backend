package com.sigl.sigl.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apprentice extends User {

  private int currentYear;

    private String majorSpecialization;

    private String minorSpecialization;

  @ManyToOne
  private ApprenticeMentor apprenticeMentor;

  @ManyToOne
  private EducationalTutor educationalTutor;



  @OneToMany(mappedBy = "apprentice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Event> events;

}
