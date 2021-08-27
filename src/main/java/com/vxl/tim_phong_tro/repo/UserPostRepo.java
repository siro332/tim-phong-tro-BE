package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostRepo extends JpaRepository<UserPost,Long> {
    List<UserPost> findByAppUser(AppUser appUser);
}
