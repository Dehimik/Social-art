package com.dehimik.art.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Getter
    private String role;  // Роль користувача в проекті

    @Setter
    private LocalDateTime joinedAt;
}
