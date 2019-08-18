package com.example.springvue.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Getter@Setter@NoArgsConstructor
public class SignUpRequestVO {
    @NotBlank
    @Size(min = 4, max = 40)
    private String nickName;

    @NotBlank
    @Size(min = 3, max = 15)
    private String userName;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    public SignUpRequestVO(@NotBlank @Size(min = 4, max = 40) String nickName, @NotBlank @Size(min = 3, max = 15) String userName, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(min = 6, max = 20) String password) {
        this.nickName = nickName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
