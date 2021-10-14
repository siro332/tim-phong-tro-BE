package com.vxl.tim_phong_tro.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uid;
    private String email;
    private Boolean emailVerified;
    @ManyToMany
    private Set<UserRole> userRoles;

//    @OneToOne(mappedBy = "user")
//    @PrimaryKeyJoinColumn
//    private UserInfo userInfo;
}
