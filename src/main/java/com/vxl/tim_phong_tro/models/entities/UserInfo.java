package com.vxl.tim_phong_tro.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserInfo {
    @Id
    @Column(name="user_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String image;
    private String description;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private AppUser user;
}
