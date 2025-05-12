package com.dehimik.art.dto.post;

import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String content;
    private Instant createdAt;
}
