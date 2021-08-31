package com.vxl.tim_phong_tro.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vxl.tim_phong_tro.converters.DtoConverter;
import com.vxl.tim_phong_tro.models.dtos.UserInfoDto;
import com.vxl.tim_phong_tro.models.dtos.UserPostDto;
import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.UserPost;
import com.vxl.tim_phong_tro.models.entities.UserRole;
import com.vxl.tim_phong_tro.services.AppUserService;
import com.vxl.tim_phong_tro.services.FirebaseFileService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final AppUserService appUserService;
    private final DtoConverter appUserConverter;
    private final FirebaseFileService firebaseFileService;

    @GetMapping("/user/info/{uid}")
    public ResponseEntity<UserInfoDto> getUser(@PathVariable String uid) {
        return ResponseEntity.ok().body(appUserConverter.userInfoEntityToDto(appUserService.getUserInfoByUid(uid)));
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<UserRole> saveUserRole(@RequestBody UserRole role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveRole(role));
    }

    @PostMapping("/role/add_roles_to_user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        ArrayList<String> roleNames = form.getRoles();
        Set<UserRole> roles = new HashSet<>();
        for (String roleName : roleNames
        ) {
            roles.add(appUserService.getRole(roleName));
        }
        appUserService.setRoleToUser(form.getUsername(), roles);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{uid}/posts")
    public ResponseEntity<Map<String, Object>> getUserPosts(@PathVariable String uid,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size) {
        try {
            Page<UserPost> pagePosts = appUserService.getUserPosts(uid, PageRequest.of(page,size));
            List<UserPost> posts = pagePosts.getContent();
            List<UserPostDto> postDtos = new ArrayList<>();
            for (UserPost post:posts
                 ) {
                postDtos.add(appUserConverter.userPostEntityToDto(post));
            }
            Map<String, Object> response = new HashMap<>();
            response.put("posts", postDtos);
            response.put("currentPage", pagePosts.getNumber());
            response.put("totalItems", pagePosts.getTotalElements());
            response.put("totalPages", pagePosts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user/info/save/{uid}")
    public ResponseEntity<?> saveUserInfo(@PathVariable String uid, @RequestHeader("Authorization") String authToken, @RequestBody UserInfoDto userInfoDto) throws FirebaseAuthException {
        String tokenString = authToken.substring("Bearer ".length());
        FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(tokenString);
        try {
            if (token.getUid().equals(uid)) {
                appUserService.saveUserInfo(uid, userInfoDto);
                return ResponseEntity.ok().body("Saved to database");
            } else throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving to database");
        }
    }

    @PostMapping("/user/info/save/{uid}/profile_pic")
    public ResponseEntity<?> upload(@PathVariable String uid, @RequestHeader("Authorization") String authToken, @RequestParam("file") MultipartFile multipartFile) throws IOException, FirebaseAuthException {
        log.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
        String tokenString = authToken.substring("Bearer ".length());
        FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(tokenString);
        try {
            if (token.getUid().equals(uid)) {
                String fileName = firebaseFileService.saveFile(multipartFile);
                String imageUrl = firebaseFileService.getImageUrl(fileName);
                appUserService.saveUserAvatar(uid, imageUrl);
                return ResponseEntity.ok().body(imageUrl);
            } else throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}

@Data
class RoleToUserForm {
    private String username;
    private ArrayList<String> roles;
}
