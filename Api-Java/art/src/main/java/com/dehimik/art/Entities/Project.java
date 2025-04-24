package com.dehimik.art.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="\"projects\"")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    @Column(name = "media_url")
    private String mediaUrl;

    @OneToMany(mappedBy = "project")
    private Set<ProjectMember> members = new HashSet<>();

    @Setter
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
