package com.dehimik.art.dto.post;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class CommentRequest {
    @NotNull(message = "userId is required")
    private Long userId;

    @NotBlank(message = "content cannot be blank")
    @Size(max = 500, message = "content must be at most 500 chars")
    private String content;
}
