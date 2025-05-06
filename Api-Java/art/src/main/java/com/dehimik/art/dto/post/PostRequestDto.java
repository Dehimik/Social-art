package com.dehimik.art.dto.post;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@Data
public class PostRequestDto {
    @URL(message = "Post url must be valid")
    private String mediaUrl;

    @Size(max = 500, message = "Post description cannot exceed 500 characters")
    private String postText;
}
