package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByEmail(String email);
    AppUser findByUid(String uid);
    Boolean existsByEmail(String email);
}
