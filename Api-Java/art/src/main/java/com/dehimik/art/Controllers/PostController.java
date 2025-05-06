package com.dehimik.art.Controllers;

import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.post.PostRequestDto;
import com.dehimik.art.dto.user.UserResponseDto;
import com.dehimik.art.services.PostService;
import com.dehimik.art.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto, Long userId) {
        return ResponseEntity.ok(postService.createPost(dto, userId));
    }

    @GetMapping
    public ResponseEntity<?> getMyPosts(Principal principal) {
        Long userId = userService.getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserPosts(@PathVariable String username) {
        UserResponseDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(postService.getPostsByUser(user.getId()));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> patchPost(@PathVariable Long postId, @RequestBody PostRequestDto dto, Long userId /*Principal principal*/) {
        //Long userId = userService.getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(postService.updatePost(postId, dto, userId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, Long userId /*Principal principal*/) {
        //Long userId = userService.getUserIdFromPrincipal(principal);
        postService.deletePost(postId, userId);
        return ResponseEntity.ok().build();
    }

}

