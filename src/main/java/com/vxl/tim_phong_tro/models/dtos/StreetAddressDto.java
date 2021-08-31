package com.vxl.tim_phong_tro.models.dtos;

import com.vxl.tim_phong_tro.models.entities.Ward;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreetAddressDto {
    private Long id;
    private String streetName;
    private String houseNumber;
    private WardDto ward;
}
