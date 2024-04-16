package com.sigl.sigl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Document")
@Data
public abstract class Document {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnore
    private User author;

    private Date uploadDate;

    private Date startDate;

    private Date deadline;

    private String comment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Viva viva;

    private String hash;

    private String url;

    private String keyword1;

    private String keyword2;

    private String keyword3;

    private String keyword4;

}
