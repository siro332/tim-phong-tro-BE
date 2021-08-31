package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInfoRepo extends JpaRepository<RoomInfo,Long> {
}
