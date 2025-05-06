package com.dehimik.art.dto.post;

import com.dehimik.art.Entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Data
@AllArgsConstructor
@Getter
public class PostResponseDto {

    private String mediaUrl;
    private String postText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        this.mediaUrl = post.getMediaUrl();
        this.postText = post.getPostText();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
