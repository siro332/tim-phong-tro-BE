package com.vxl.tim_phong_tro.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer code;
    private String codename;
    private String divisionType;
    private String shortCodename;
    private String image;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
