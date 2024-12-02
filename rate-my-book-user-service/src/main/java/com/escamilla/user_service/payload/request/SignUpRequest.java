package com.escamilla.user_service.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Date dateBirth;
    private String gender;
    private String[] favoriteTopics;
}
