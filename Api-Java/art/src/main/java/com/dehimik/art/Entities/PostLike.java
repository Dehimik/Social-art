package com.dehimik.art.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "likes")
@Data @NoArgsConstructor @AllArgsConstructor
public class PostLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;
}
