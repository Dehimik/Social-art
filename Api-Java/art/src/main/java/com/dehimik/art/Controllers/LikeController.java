package com.dehimik.art.Controllers;

import com.dehimik.art.dto.post.*;
import com.dehimik.art.services.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor @Validated
public class LikeController {
    private final LikeService svc;

    @PostMapping
    public ResponseEntity<LikeResponse> like(
            @PathVariable Long postId,
            @Valid @RequestBody LikeRequest req
    ) {
        return ResponseEntity.status(201)
                .body(svc.like(postId, req));
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        svc.unlike(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long count(@PathVariable Long postId) {
        return svc.count(postId);
    }
}
