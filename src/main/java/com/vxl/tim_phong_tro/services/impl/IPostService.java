package com.vxl.tim_phong_tro.services.impl;

import com.vxl.tim_phong_tro.converters.DtoConverter;
import com.vxl.tim_phong_tro.models.dtos.PostForm;
import com.vxl.tim_phong_tro.models.dtos.UserPostDto;
import com.vxl.tim_phong_tro.models.entities.*;
import com.vxl.tim_phong_tro.repo.*;
import com.vxl.tim_phong_tro.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IPostService implements PostService {
    final private DtoConverter dtoConverter;
    final private UserPostRepo userPostRepo;
    final private AppUserRepo appUserRepo;
    final private CityRepo cityRepo;
    final private DistrictRepo districtRepo;
    final private WardRepo wardRepo;
    final private StreetAddressRepo streetAddressRepo;
    final private RoomInfoRepo roomInfoRepo;
    final private SavedPostRepo savedPostRepo;
    @Override
    public Page<UserPost> getUserPosts(String appUserUid,Pageable paging) {
        Page<UserPost> pagePosts;
        pagePosts = userPostRepo.findByAppUser_UidOrderByPostingDateDesc(appUserUid, paging);
        return pagePosts;
    }

    @Override
    public UserPost createPost(String uid, PostForm form) {
        AppUser user = appUserRepo.findByUid(uid);
        Ward ward = wardRepo.findByCode(form.getRoomInfo().getAddress().getWard().getCode());
        StreetAddress address = dtoConverter.addressDtoToEntity(form.getRoomInfo().getAddress());
        address.setWard(ward);
        StreetAddress savedAddress = streetAddressRepo.save(address);
        RoomInfo roomInfo = dtoConverter.roomInfoDtoToEntity(form.getRoomInfo());
        roomInfo.setAddress(savedAddress);
        RoomInfo savedRoomInfo = roomInfoRepo.save(roomInfo);
        UserPost post = dtoConverter.postFormToEntity(form);
        post.setAppUser(user);
        post.setRoomInfo((savedRoomInfo));
        post.setPostingDate(new Date());
        userPostRepo.save(post);
        return post;
    }

    @Override
    public Page<UserPost> getAllPosts(Pageable paging) {
        Page<UserPost> pagePosts;
        pagePosts = userPostRepo.findAll(paging);
        return pagePosts;
    }

    @Override
    public Page<UserPost> getPostContains(String searchString, Pageable pageable, Specification<UserPost> spec) {
        Page<UserPost> pagePosts;
//        pagePosts = userPostRepo.findUserPostContains(searchString.toLowerCase(), pageable,spec);
        pagePosts = userPostRepo.findAll(spec,pageable);
        return pagePosts;
    }

    @Override
    public Page<UserPost> getUserSavedPost(String uid, Pageable pageable) {
        Page<UserPost> pagePosts;
        pagePosts = savedPostRepo.findPostByAppUser_Uid(uid, pageable);
        log.info(pagePosts.getContent().toString());
        return pagePosts;
    }

    @Override
    public Optional<UserPost> getPost(Long id){
            return userPostRepo.findById(id);
    }

    @Override
    public void addCity(Long id, Iterable<Ward> districts) {
        Optional<District> a = districtRepo.findById(id);
        if (a.isPresent()){
            for (Ward district:districts
                 ) {
                district.setDistrict(a.get());
            }
            wardRepo.saveAll(districts);
        }
    }

    @Override
    public void deletePost(Long id) {
        userPostRepo.deleteById(id);
    }

    @Override
    public void savePostImages(UserPost post, String images) {
        post.getRoomInfo().setImage(images);
        post.setThumbnailImage(images.split(";")[0]);
        userPostRepo.save(post);
    }
}
