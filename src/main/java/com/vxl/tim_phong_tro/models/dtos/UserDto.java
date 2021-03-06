package com.vxl.tim_phong_tro.models.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String uid;
    private String email;
    private Boolean emailVerified;
    private Set<UserRoleDto> userRoles;
    private UserInfoDto userInfo;
}
