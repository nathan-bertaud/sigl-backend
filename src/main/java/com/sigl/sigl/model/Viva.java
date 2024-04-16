package com.sigl.sigl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Viva extends Event{

    @ManyToMany
    private List<User> juries;

    private Mark mark;

    private Status status;

    @OneToMany(mappedBy = "viva", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;
}
