package com.vxl.tim_phong_tro.models.dtos;

import com.vxl.tim_phong_tro.models.entities.District;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WardDto {
    private Long id;
    private String name;
    private String image;
    private DistrictDto district;
}
