package com.vxl.tim_phong_tro.services;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vxl.tim_phong_tro.models.dtos.AuthToken;
import com.vxl.tim_phong_tro.models.dtos.UserInfoDto;
import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.UserInfo;
import com.vxl.tim_phong_tro.models.entities.UserPost;
import com.vxl.tim_phong_tro.models.entities.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface AppUserService {
    AppUser getUserByUid(String uid);
    AppUser getUserByEmail(String email);
    UserInfo getUserInfoByUid(String uid);
    void saveUserInfo(String uid, UserInfoDto info);
    void registerUser(FirebaseToken token) throws ExecutionException, InterruptedException, FirebaseAuthException;
    AppUser saveUser(AppUser user);
    UserRole getRole(String name);
    UserRole saveRole(UserRole role);
    void setRoleToUser(String userEmail, Set<UserRole> roleSet);
    Boolean isEmailUsed(String email);

    Page<UserPost> getUserPosts(String appUserUid, Pageable paging);

    void saveUserAvatar(String uid, String imageUrl);
}
