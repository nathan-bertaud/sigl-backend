package com.sigl.sigl.dto;

import com.sigl.sigl.model.Semestre;
import lombok.Data;

import java.util.Date;

@Data
public class EventForm {

    private String title;
    private Date startDate;
    private Date endDate;
    private Semestre semestre;
}
