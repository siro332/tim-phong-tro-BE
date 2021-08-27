package com.vxl.tim_phong_tro.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vxl.tim_phong_tro.converters.AppUserConverter;
import com.vxl.tim_phong_tro.models.dtos.AuthToken;
import com.vxl.tim_phong_tro.models.entities.AppUser;
import com.vxl.tim_phong_tro.models.entities.UserInfo;
import com.vxl.tim_phong_tro.models.entities.UserRole;
import com.vxl.tim_phong_tro.services.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody AuthToken request) throws FirebaseAuthException, ExecutionException, InterruptedException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(request.getToken()).get();
        if (appUserService.getUserByUid(decodedToken.getUid()) == null) {
            try{
                appUserService.registerUser(decodedToken);
                return ResponseEntity.ok().body("Created user with uid: " + decodedToken.getUid());

            }catch (Exception e){
                return ResponseEntity.badRequest().body("Error: " + e.toString());

            }
        }
        return ResponseEntity.badRequest().body("User with uid: " + decodedToken.getUid() + " is already exist");
    }
}
