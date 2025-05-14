package com.dehimik.art.dto.post;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class LikeResponse {
    private Long id;
    private Long userId;
    private Long postId;
}
