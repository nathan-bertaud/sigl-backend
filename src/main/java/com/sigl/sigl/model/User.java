package com.sigl.sigl.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<Document> documents;

  private String name;

  private String firstName;

  private Date birthDate;

  @Column(unique = true)
  private String email;

  private String password;

    @ManyToMany
    private List<Viva> vivas;

    private Date lastConnexionDate;
}
