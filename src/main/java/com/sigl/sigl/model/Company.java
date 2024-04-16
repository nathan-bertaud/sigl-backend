package com.sigl.sigl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private boolean privateCompany;

    private boolean publicCompany;

    private String name;

    private String address;

    private int zipCode;

    private String city;

    private String phoneNumber;

    private String email;

    private String siret;

    private String bossType;

    private String specificBoss;

    private String activityCode;

    private long employeeNumber;

    private String convention;

    private String idcCode;

}
