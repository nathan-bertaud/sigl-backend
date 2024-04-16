package com.sigl.sigl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm {
    private String password;
    private String newPassword;
}
