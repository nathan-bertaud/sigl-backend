package com.sigl.sigl.dto;

import com.sigl.sigl.model.Semestre;
import lombok.Data;
import java.util.Date;

@Data
public class BiannualMeetingForm {

    private Date startDate;
    private Date endDate;
    private String place;
    private Semestre semestre;
    private String title;

}
