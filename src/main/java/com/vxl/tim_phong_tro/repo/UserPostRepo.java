package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.UserPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepo extends JpaRepository<UserPost,Long> {
    @Override
    Page<UserPost> findAll(Pageable pageable);
    Page<UserPost> findByAppUser_Uid(String uid,Pageable pageable);
}
