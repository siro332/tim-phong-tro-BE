package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.SavedPost;
import com.vxl.tim_phong_tro.models.entities.UserPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SavedPostRepo extends JpaRepository<SavedPost,Long> {
    @Query("Select p.post from SavedPost p where p.appUser.uid = :uid")
    Page<UserPost> findPostByAppUser_Uid(String uid, Pageable pageable);
}
