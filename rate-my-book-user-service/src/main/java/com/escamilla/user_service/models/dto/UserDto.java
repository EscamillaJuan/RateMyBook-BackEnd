package com.escamilla.user_service.models.dto;

import com.escamilla.user_service.models.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private Role role;
    private String userName;
    private String name;
    private String lastName;
    private String profilePictureUri;
    private Date dateBirth;
    private String gender;
    private String[] favoriteTopics;
}
