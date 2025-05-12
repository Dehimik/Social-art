package com.dehimik.art.dto.post;

import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class LikeDto {
    private Long id;
    private Long userId;
    private Long postId;
}
