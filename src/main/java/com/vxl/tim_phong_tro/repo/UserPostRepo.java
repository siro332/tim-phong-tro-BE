package com.vxl.tim_phong_tro.repo;

import com.vxl.tim_phong_tro.models.entities.UserPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepo extends JpaRepository<UserPost,Long>, JpaSpecificationExecutor<UserPost> {
    Page<UserPost> findAll(Pageable pageable);
    Page<UserPost> findByAppUser_UidOrderByPostingDateDesc(String uid,Pageable pageable);
    @Query("select p from UserPost p where lower(p.name) like %:searchString% or " +
            "lower(p.roomInfo.address.streetName) like %:searchString% or " +
            "lower(p.roomInfo.address.houseNumber) like %:searchString% or " +
            "lower(p.roomInfo.address.ward.name) like %:searchString% or " +
            "lower(p.roomInfo.address.ward.district.name) like %:searchString% or " +
            "lower(p.roomInfo.address.ward.district.city.name) like %:searchString%")
    Page<UserPost> findUserPostContains(String searchString, Pageable pageable, Specification<UserPost> spec);
}
