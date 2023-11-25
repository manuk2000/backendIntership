package com.picsart.intership.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
}
