package com.vxl.tim_phong_tro.services;

import com.vxl.tim_phong_tro.models.dtos.PostForm;
import com.vxl.tim_phong_tro.models.entities.UserPost;
import com.vxl.tim_phong_tro.models.entities.Ward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface PostService {

    Page<UserPost> getUserPosts(String appUserUid, Pageable paging);
    UserPost createPost(String uid,PostForm form);
    Page<UserPost> getAllPosts(Pageable paging);
    Page<UserPost> getPostContains(Pageable pageable, Specification<UserPost> spec);
    Page<UserPost> getUserSavedPost(String uid, Pageable pageable);
    Optional<UserPost> getPost(Long id);
    void addCity(Long id, Iterable<Ward> city);
    void deletePost(Long id);
    void savePostImages(UserPost post, String toString);
}
