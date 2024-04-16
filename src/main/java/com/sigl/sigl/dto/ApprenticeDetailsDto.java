package com.sigl.sigl.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sigl.sigl.model.EducationalTutor;
import com.sigl.sigl.repository.EducationalTutorRepository;
import lombok.*;
import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.EducationalTutor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApprenticeDetailsDto {

  private Long id;
  private String name;
  private String firstName;
  private String email;
  @JsonDeserialize(as = EducationalTutor.class)
  private EducationalTutor educationalTutor;

  public static ApprenticeDetailsDto fromApprentice(Apprentice apprentice) {
    return new ApprenticeDetailsDto(
      apprentice.getId(),
      apprentice.getName(),
      apprentice.getFirstName(),
      apprentice.getEmail(),
      apprentice.getEducationalTutor()
    );
  }
}
