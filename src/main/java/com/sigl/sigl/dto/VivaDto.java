package com.sigl.sigl.dto;

import com.sigl.sigl.model.Apprentice;
import com.sigl.sigl.model.Semestre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class VivaDto {
    private Long id;
    private List<UserNameFirstNameDto> juries;
    private Apprentice apprentice;
    private String title;
    private Date startDate;
    private Date endDate;
    private String place;
    private Semestre semestre;
}
