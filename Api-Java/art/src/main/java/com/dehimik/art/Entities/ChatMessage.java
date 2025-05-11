package com.dehimik.art.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"messages\"")
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Getter
    @Setter
    private ChatGroup group;

    @ManyToOne
    @Getter
    @Setter
    private User sender;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
