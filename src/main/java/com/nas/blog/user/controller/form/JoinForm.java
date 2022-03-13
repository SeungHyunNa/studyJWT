package com.nas.blog.user.controller.form;

import com.nas.blog.user.model.RoleType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class JoinForm {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String username;

    private RoleType role = RoleType.ROLE_USER;
}
