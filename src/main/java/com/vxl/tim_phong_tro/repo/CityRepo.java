package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<City,Long> {
}
