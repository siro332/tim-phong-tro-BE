package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.UserPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepo extends JpaRepository<UserPost,Long>, JpaSpecificationExecutor<UserPost> {
    Page<UserPost> findAll(Pageable pageable);
    Page<UserPost> findByAppUser_UidOrderByPostingDateDesc(String uid,Pageable pageable);
}
