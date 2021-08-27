package com.vxl.tim_phong_tro.models.dtos;

import com.vxl.tim_phong_tro.models.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {
    private String token;
}
