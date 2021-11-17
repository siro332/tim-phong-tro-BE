package com.vxl.tim_phong_tro.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.vxl.tim_phong_tro.converters.DtoConverter;
import com.vxl.tim_phong_tro.models.dtos.PostDto;
import com.vxl.tim_phong_tro.models.dtos.PostForm;
import com.vxl.tim_phong_tro.models.dtos.PostPreviewDto;
import com.vxl.tim_phong_tro.models.entities.UserInfo;
import com.vxl.tim_phong_tro.models.entities.UserPost;
import com.vxl.tim_phong_tro.models.entities.Ward;
import com.vxl.tim_phong_tro.models.specifications.UserPost.UserPostSpecificationsBuilder;
import com.vxl.tim_phong_tro.services.AppUserService;
import com.vxl.tim_phong_tro.services.FirebaseFileService;
import com.vxl.tim_phong_tro.services.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.vxl.tim_phong_tro.models.specifications.SearchOperation.OR_PREDICATE_FLAG;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final DtoConverter dtoConverter;
    private final FirebaseFileService firebaseFileService;
    private final AppUserService appUserService;

    @GetMapping("/posts")
    public ResponseEntity<Map<String, Object>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "3") int size,
                                                           @RequestParam(defaultValue = "postingDate") String sortParam,
                                                           @RequestParam(defaultValue = "0") int sortDirection) {
        try {
            Page<UserPost> pagePosts;
            if (sortDirection == 0) {
                pagePosts = postService.getAllPosts(PageRequest.of(page, size, Sort.by(sortParam)));
            } else {
                pagePosts = postService.getAllPosts(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortParam)));
            }
            Map<String, Object> response = getResponse(pagePosts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/posts/search")
    public ResponseEntity<Map<String, Object>> getPostsContains(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "0") int sortDirection,
            @RequestParam(defaultValue = "postingDate") String sortParam,
            @RequestBody List<Filter> searchCriteriaList) {
        try {
            UserPostSpecificationsBuilder builder = new UserPostSpecificationsBuilder();
            for (Filter criteria : searchCriteriaList) {
                if (criteria.orPredicate != null && criteria.orPredicate.equals(OR_PREDICATE_FLAG)) {
                    if (criteria.value.toString().contains("*")) {
                        builder.with(criteria.orPredicate, criteria.key, criteria.operation, criteria.value.toString().replaceAll("\\*", ""), criteria.value.toString(), criteria.value.toString());
                    } else {
                        builder.with(criteria.orPredicate, criteria.key, criteria.operation, criteria.value, criteria.value.toString(), criteria.value.toString());
                    }
                } else {
                    if (criteria.value.toString().contains("*")) {
                        builder.with(criteria.key, criteria.operation, criteria.value.toString().replaceAll("\\*", ""), criteria.value.toString(), criteria.value.toString());
                    } else {
                        builder.with(criteria.key, criteria.operation, criteria.value, criteria.value.toString(), criteria.value.toString());
                    }
                }
            }

            Specification<UserPost> spec = builder.build();
            Page<UserPost> pagePosts;
            if (sortDirection == 0) {
                pagePosts = postService.getPostContains(PageRequest.of(page, size, Sort.by(sortParam)), spec);
            } else {
                pagePosts = postService.getPostContains(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortParam)), spec);
            }
            Map<String, Object> response = getResponse(pagePosts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/{uid}")
    public ResponseEntity<Map<String, Object>> getUserPosts(@PathVariable String uid,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size) {
        try {
            Page<UserPost> pagePosts = postService.getUserPosts(uid, PageRequest.of(page, size));
            Map<String, Object> response = getResponse(pagePosts);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/saved/{uid}")
    public ResponseEntity<Map<String, Object>> getAllPosts(@PathVariable String uid,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "3") int size,
                                                           @RequestParam(defaultValue = "id") String sortParam,
                                                           @RequestParam(defaultValue = "0") int sortDirection) {
        try {
            Page<UserPost> pagePosts;
            if (sortDirection == 0) {
                pagePosts = postService.getUserSavedPost(uid, PageRequest.of(page, size, Sort.by(sortParam)));
            } else {
                pagePosts = postService.getUserSavedPost(uid, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortParam)));
            }
            Map<String, Object> response = getResponse(pagePosts);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/detail/{id}")
    public ResponseEntity<?> getPostDetail(@PathVariable Long id) {
        try {
            Optional<UserPost> postOptional = postService.getPost(id);
            if (postOptional.isPresent()) {
                UserInfo info = appUserService.getUserInfoByUid(postOptional.get().getAppUser().getUid());
                PostDto postDto = dtoConverter.userPostEntityToPostDto(postOptional.get());
                postDto.getAppUser().setUserInfo(dtoConverter.userInfoEntityToDto(info));
                return ResponseEntity.ok().body(postDto);
            } else {
                return ResponseEntity.badRequest().body("Post with id: " + id + " not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error:" + e);
        }
    }

    @PostMapping("/posts/create/{uid}")
    public ResponseEntity<?> createPost(@PathVariable String uid, @RequestHeader("Authorization") String authToken, @RequestParam("form") String formString, @RequestParam("files") MultipartFile[] files) throws FirebaseAuthException {
        String tokenString = authToken.substring("Bearer ".length());
        FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(tokenString);
        try {
            if (token.getUid().equals(uid)) {
                log.info(formString);
                PostForm form = new ObjectMapper().readValue(formString, PostForm.class);
                UserPost post = postService.createPost(uid, form);
                StringBuilder imageUrls = new StringBuilder();
                for (MultipartFile file : files
                ) {
                    String fileName = firebaseFileService.saveFile(file);
                    String imageUrl = firebaseFileService.getImageUrl(fileName);
                    imageUrls.append(imageUrl).append(";");
                }
                postService.savePostImages(post, imageUrls.toString());
                return ResponseEntity.ok().body(dtoConverter.userPostEntityToPostDto(post));
            } else {
                throw new Exception("UID not match!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating post!" + e);
        }
    }

    @DeleteMapping("/posts/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @RequestHeader("Authorization") String authToken,
                                        @RequestParam String uid) throws FirebaseAuthException {
        String tokenString = authToken.substring("Bearer ".length());
        FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(tokenString);
        try {
            if (token.getUid().equals(uid)) {
                postService.deletePost(id);
                return ResponseEntity.ok().body("Post with id: " + id + " is deleted");
            } else {
                throw new Exception("UID not match!");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e);
        }
    }

    @PostMapping("/user/addProvinces/{id}")
    public ResponseEntity<?> addProvinces(@PathVariable Long id, @RequestBody Iterable<Ward> cities) {
        try {
            postService.addCity(id, cities);
            return ResponseEntity.ok().body("Ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    private Map<String, Object> getResponse(Page<UserPost> pagePosts) {
        List<UserPost> posts = pagePosts.getContent();
        List<PostPreviewDto> postDtos = new ArrayList<>();
        for (UserPost post : posts
        ) {
            postDtos.add(dtoConverter.userPostEntityToPostPreviewDto(post));
        }
        Map<String, Object> response = new HashMap<>();
        response.put("posts", postDtos);
        response.put("currentPage", pagePosts.getNumber());
        response.put("totalItems", pagePosts.getTotalElements());
        response.put("totalPages", pagePosts.getTotalPages());
        return response;
    }

}

@Data
class Filter {
    String orPredicate;
    String key;
    String operation;
    Object value;
}