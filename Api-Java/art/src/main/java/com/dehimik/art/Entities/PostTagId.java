package com.dehimik.art.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor
public class PostTagId implements Serializable {
    private Long postId;
    private Long tagId;
}
