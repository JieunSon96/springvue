package com.example.springvue.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter@Setter
public class LoginRequestVO {
    @NotBlank
    private String userNameOrEmail;

    @NotBlank
    private String password;
}
