package com.vxl.tim_phong_tro.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPreviewDto {
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
    private String thumbnailImage;
    private Date postingDate;
    private Boolean isVerified;
    private RoomInfoPreviewDto roomInfo;
}
