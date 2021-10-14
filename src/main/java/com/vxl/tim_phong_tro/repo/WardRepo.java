package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepo extends JpaRepository<Ward,Long> {
    Ward findByCode(Integer code);
}
