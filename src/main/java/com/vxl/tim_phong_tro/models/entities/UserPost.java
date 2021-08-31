package com.vxl.tim_phong_tro.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
    private String thumbnailImage;
    private Date postingDate;
    private Boolean isVerified;
    @OneToOne
    @JoinColumn(name = "room_info_id")
    private RoomInfo roomInfo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

}
