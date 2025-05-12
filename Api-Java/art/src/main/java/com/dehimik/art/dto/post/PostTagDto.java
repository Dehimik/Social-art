package com.dehimik.art.dto.post;

import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class PostTagDto {
    private Integer postId;
    private Integer tagId;
}
