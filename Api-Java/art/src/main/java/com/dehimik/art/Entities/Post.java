package com.dehimik.art.Entities;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"posts\"")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    private String mediaUrl;

    @Setter
    private String postText;

    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;
}
