package com.sigl.sigl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiannualAssessmentS10 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Apprentice apprentice;

    private int valueDiag;
    private String commDiag;

    private int valueConcevoir;
    private String commConcevoir;

    private int valueProduire;
    private String commProduire;

    private int valueValider;
    private String commValider;

    private int valuePiloter;
    private String commPiloter;

    private int valueAdapter;
    private String commAdapter;

    private int valueCommuniquer;
    private String commCommuniquer;

    private String comMA;
    private String comTutor;

    public void setApprentice(Apprentice apprentice) {
        this.apprentice = apprentice;
    }
}
