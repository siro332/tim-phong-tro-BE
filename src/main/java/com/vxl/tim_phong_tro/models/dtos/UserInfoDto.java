package com.vxl.tim_phong_tro.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String image;
    private String description;
    private UserDto user;
}
