package com.vxl.tim_phong_tro.services;

import com.vxl.tim_phong_tro.models.entities.City;
import com.vxl.tim_phong_tro.models.entities.District;
import com.vxl.tim_phong_tro.models.entities.Ward;

import java.util.List;

public interface AddressService {
    List<City> getListOfCities();
    List<District> getListOfDistrictsByCityId(Long id);
    List<Ward> getListOfWardsByDistrictId(Long id);
}
