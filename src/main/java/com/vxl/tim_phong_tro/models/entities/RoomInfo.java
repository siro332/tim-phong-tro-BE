package com.vxl.tim_phong_tro.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @OneToOne
    @JoinColumn(name = "address_id")
    private StreetAddress address;
    @ManyToMany
    private Set<RoomUtil> roomUtils;
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
}
