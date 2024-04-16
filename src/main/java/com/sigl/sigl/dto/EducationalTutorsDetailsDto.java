package com.sigl.sigl.dto;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.EducationalTutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EducationalTutorsDetailsDto {
    private String name;
    private String firstName;
    private String email;

    public static EducationalTutorsDetailsDto fromEducationalTutor(EducationalTutor educationalTutor) {
        return new EducationalTutorsDetailsDto(
                educationalTutor.getName(),
                educationalTutor.getFirstName(),
                educationalTutor.getEmail()
        );
    }
}
