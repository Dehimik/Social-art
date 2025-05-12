package com.dehimik.art.Controllers;

import com.dehimik.art.dto.post.CommentDto;
import com.dehimik.art.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService svc;

    @GetMapping
    public List<CommentDto> all(@PathVariable Long postId) {
        return svc.listByPost(postId);
    }

    @PostMapping
    public CommentDto create(@PathVariable Long postId,
                             @RequestBody CommentDto dto) {
        dto.setPostId(postId);
        return svc.create(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }
}