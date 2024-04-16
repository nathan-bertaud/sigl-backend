package com.sigl.sigl.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApprenticeMentorsListDto {
  private String name;
  private String firstName;
  private String email;
  private String company;
  private List<ApprenticeDetailsDto> apprentices;

}
