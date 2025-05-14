package com.dehimik.art.dto.post;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class TagRequest {
    @NotBlank(message = "name cannot be blank")
    @Size(max = 50, message = "name must be at most 50 chars")
    private String name;
}
