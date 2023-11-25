package com.picsart.intership.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ResetPassword {
    @Email(message = "Email required")
    private String email;

    @Size(min = 8, message = "Password required")
    private String newPassword;
}

