package com.vxl.tim_phong_tro.models.dtos;

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
public class PostDto {
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
    private String thumbnailImage;
    private Date postingDate;
    private Boolean isVerified;
    private RoomInfoDto roomInfo;
    private UserPostDto appUser;
}
