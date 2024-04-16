package com.sigl.sigl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Event")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Apprentice apprentice;

    private String title;
    private Date startDate;
    private Date endDate;
    private String place;
    @Enumerated(EnumType.STRING)
    private Semestre semestre;
}
