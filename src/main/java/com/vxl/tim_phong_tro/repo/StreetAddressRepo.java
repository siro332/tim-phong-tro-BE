package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.StreetAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetAddressRepo extends JpaRepository<StreetAddress,Long> {
}
