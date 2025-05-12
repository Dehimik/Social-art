package com.dehimik.art.Controllers;

import com.dehimik.art.dto.post.LikeDto;
import com.dehimik.art.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService svc;

    @PostMapping
    public LikeDto like(@PathVariable Long postId,
                        @RequestParam Long userId) {
        return svc.like(new LikeDto(null, userId, postId));
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(@PathVariable Long postId,
                                       @RequestParam Long userId) {
        svc.unlike(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long count(@PathVariable Long postId) {
        return svc.count(postId);
    }
}
