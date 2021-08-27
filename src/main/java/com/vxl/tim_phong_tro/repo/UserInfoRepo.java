package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo,Long> {
    UserInfo findByUserId(Long id);
}
