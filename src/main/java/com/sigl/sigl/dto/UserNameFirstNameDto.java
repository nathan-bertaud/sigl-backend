package com.sigl.sigl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserNameFirstNameDto {
    private Long id;
    private String name;
    private String firstName;
}
