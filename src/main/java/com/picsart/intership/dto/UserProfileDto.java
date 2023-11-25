package com.picsart.intership.dto;

import com.picsart.intership.entity.ERole;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserProfileDto {

    private String name;
    private String email;
    private ERole role;
    private String profilePicture;
    private String bio;

}
