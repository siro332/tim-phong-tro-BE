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
public class RoomInfoPreviewDto {
    private Integer gender;
    private Double area;
    private Long rentalPrice;
    private StreetAddressDto address;
    private Set<RoomUtilDto> roomUtils;
    private RoomTypeDto roomType;
}
