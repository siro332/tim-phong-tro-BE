package com.vxl.tim_phong_tro.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomUtilDto {
    private Long id;
    private String name;
    private String image;
}
