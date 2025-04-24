package com.dehimik.art.Controllers;

import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.post.PostDto;
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

//    @PostMapping
//    public ResponseEntity<?> createPost(@RequestBody PostDto dto, Principal principal) {
//        Long userId = userService.getUserIdFromPrincipal(principal);
//        return ResponseEntity.ok(postService.createPost(dto, userId));
//    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto dto, Long userId) {
        return ResponseEntity.ok(postService.createPost(dto, userId));
    }

    @GetMapping
    public ResponseEntity<?> getMyPosts(Principal principal) {
        Long userId = userService.getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserPosts(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(postService.getPostsByUser(user.getId()));
    }
}

