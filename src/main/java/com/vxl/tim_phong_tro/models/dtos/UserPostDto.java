package com.vxl.tim_phong_tro.models.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.RoomInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPostDto {
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
    private Boolean isVerified;
    private Date postingDate;
    private RoomInfoDto roomInfo;
    private UserDto user;
}
