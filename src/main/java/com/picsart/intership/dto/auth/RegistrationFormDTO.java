package com.picsart.intership.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class RegistrationFormDTO {
    @NotBlank(message = "Name required")
    private String name;

    private String email;
    @NotBlank
    private String password;
}
