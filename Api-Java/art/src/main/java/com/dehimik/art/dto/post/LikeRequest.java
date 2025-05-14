package com.dehimik.art.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class LikeRequest {
    @NotNull(message = "userId is required")
    private Long userId;
}
