package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepo extends JpaRepository<RoomType,Long> {
}
