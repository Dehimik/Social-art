package com.dehimik.art.Controllers;

import com.dehimik.art.dto.post.*;
import com.dehimik.art.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor @Validated
public class CommentController {
    private final CommentService svc;

    @GetMapping
    public List<CommentResponse> list(@PathVariable Long postId) {
        return svc.listByPost(postId);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest req
    ) {
        return ResponseEntity.status(201)
                .body(svc.create(postId, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}