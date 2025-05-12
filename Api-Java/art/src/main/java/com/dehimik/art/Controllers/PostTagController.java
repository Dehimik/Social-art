package com.dehimik.art.Controllers;

import com.dehimik.art.dto.post.PostTagDto;
import com.dehimik.art.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/tags")
@RequiredArgsConstructor
public class PostTagController {
    private final PostTagService svc;

    @PostMapping
    public ResponseEntity<Void> add(@PathVariable Long postId,
                                    @RequestBody PostTagDto dto) {
        dto.setPostId(postId);
        svc.add(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@PathVariable Long postId,
                                       @RequestBody PostTagDto dto) {
        dto.setPostId(postId);
        svc.remove(dto);
        return ResponseEntity.noContent().build();
    }
}
