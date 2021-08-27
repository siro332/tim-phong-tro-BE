package com.vxl.tim_phong_tro.services.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vxl.tim_phong_tro.models.dtos.AuthToken;
import com.vxl.tim_phong_tro.models.dtos.UserInfoDto;
import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.UserInfo;
import com.vxl.tim_phong_tro.models.entities.UserPost;
import com.vxl.tim_phong_tro.models.entities.UserRole;
import com.vxl.tim_phong_tro.repo.AppUserRepo;
import com.vxl.tim_phong_tro.repo.UserInfoRepo;
import com.vxl.tim_phong_tro.repo.UserPostRepo;
import com.vxl.tim_phong_tro.repo.UserRoleRepo;
import com.vxl.tim_phong_tro.services.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class IAppUserService implements AppUserService, UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final UserRoleRepo userRoleRepo;
    private final UserInfoRepo userInfoRepo;
    private final UserPostRepo userPostRepo;

    @Override
    public AppUser getUserByUid(String uid) {
        log.info("fetching user {}", uid);
        return appUserRepo.findByUid(uid);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        log.info("fetching user {}", email);
        return appUserRepo.findByEmail(email);
    }

    @Override
    public UserInfo getUserInfoByUid(String uid) {
        AppUser user = getUserByUid(uid);
        return userInfoRepo.findByUserId(user.getId());
    }

    @Override
    public void saveUserInfo(String uid, UserInfoDto userInfoDto) {
        AppUser user = getUserByUid(uid);
        UserInfo userInfo = userInfoRepo.findByUserId(user.getId());
        userInfo.setFirstName(userInfoDto.getFirstName());
        userInfo.setLastName(userInfoDto.getLastName());
        userInfo.setImage(userInfoDto.getImage());
        userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
        userInfo.setDescription(userInfoDto.getDescription());
        userInfoRepo.save(userInfo);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to the database", user.getUid());
        return appUserRepo.save(user);
    }

    @Override
    public UserRole getRole(String name) {
        return userRoleRepo.findByName(name);
    }

    @Override
    public UserRole saveRole(UserRole role) {
        log.info("Saving new role {} to the database", role.getName());
        return userRoleRepo.save(role);
    }

    @Override
    public void setRoleToUser(String uid, Set<UserRole> roleSet) {
        log.info("Saving roles to user with uid: {}", uid);
        appUserRepo.findByUid(uid).setUserRoles(roleSet);
    }

    @Override
    public Boolean isEmailUsed(String email) {
        return appUserRepo.existsByEmail(email);
    }

    @Override
    public List<UserPost> getUserPosts(AppUser appUser) {
        return userPostRepo.findByAppUser(appUser);
    }

    @Override
    public void saveUserAvatar(String uid, String imageUrl) {
        AppUser user = getUserByUid(uid);
        UserInfo userInfo = userInfoRepo.findByUserId(user.getId());
        userInfo.setImage(imageUrl);
        userInfoRepo.save(userInfo);
    }

    @Override
    public void registerUser(FirebaseToken decodedToken) throws ExecutionException, InterruptedException, FirebaseAuthException {
            Set<UserRole> roles = new HashSet<>();
            roles.add(userRoleRepo.findByName("ROLE_USER"));
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", "ROLE_USER");
            try {
                FirebaseAuth.getInstance().setCustomUserClaims(decodedToken.getUid(), claims);
                AppUser newUser =
                        AppUser.builder().uid(decodedToken.getUid())
                                .email(decodedToken.getEmail())
                                .emailVerified(decodedToken.isEmailVerified())
                                .userRoles(roles)
                                .build();
                appUserRepo.save(newUser);
                UserInfo newUserInfo =
                        UserInfo.builder()
                                .user(appUserRepo.findByUid(newUser.getUid()))
                                .firstName(decodedToken.getName() != null ? decodedToken.getName() : "")
                                .lastName("")
                                .phoneNumber("")
                                .description("")
                                .image(decodedToken.getPicture() != null ? decodedToken.getPicture() : "").build();
                userInfoRepo.save(newUserInfo);
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
            }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        AppUser user = appUserRepo.findByUid(userId);
        if (user == null) {
            log.error("user not found in the database");
            throw new UsernameNotFoundException("user not found in the database");
        } else {
            log.info("user found in the database: {}", userId);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<UserRole> userRoles = user.getUserRoles();
        for (UserRole role : userRoles
        ) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(user.getUid(), null, authorities);
    }
}
