package com.dehimik.art.dto.post;

import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private Instant createdAt;
}
