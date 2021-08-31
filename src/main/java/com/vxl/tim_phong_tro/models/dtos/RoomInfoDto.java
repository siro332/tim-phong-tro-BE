package com.vxl.tim_phong_tro.models.dtos;

import com.vxl.tim_phong_tro.models.entities.RoomType;
import com.vxl.tim_phong_tro.models.entities.RoomUtil;
import com.vxl.tim_phong_tro.models.entities.StreetAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInfoDto {
    private Long id;
    private String name;
    private Integer capacity;
    private Integer gender;
    private Double area;
    private Long rentalPrice;
    private Long deposit;
    private Long electricityCost;
    private Long waterCost;
    private Long internetCost;
    private Long parkingCost;
    private Long otherExpense;
    private String image;
    private StreetAddressDto address;
    private Set<RoomUtilDto> roomUtils;
    private RoomTypeDto roomType;
}
