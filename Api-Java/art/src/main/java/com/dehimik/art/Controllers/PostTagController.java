package com.dehimik.art.Controllers;

import com.dehimik.art.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/posts/{postId}/tags")
@RequiredArgsConstructor
public class PostTagController {
    private final PostService postSvc;

    @PostMapping
    public ResponseEntity<Void> addTag(
            @PathVariable Long postId,
            @RequestParam Long tagId
    ) {
        postSvc.addTagToPost(postId, tagId);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeTag(
            @PathVariable Long postId,
            @RequestParam Long tagId
    ) {
        postSvc.removeTagFromPost(postId, tagId);
        return ResponseEntity.noContent().build();
    }
}
