package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepo extends JpaRepository<District,Long> {
}
