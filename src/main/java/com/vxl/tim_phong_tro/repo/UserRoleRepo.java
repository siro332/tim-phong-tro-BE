package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole,Long> {
    UserRole findByName(String name);
}
