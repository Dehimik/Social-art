package com.dehimik.art.Entities;

import com.dehimik.art.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"chat_members\"")
@Data
public class ChatMember {
    @EmbeddedId
    private ChatMemberId id;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    @Getter
    @Setter
    private ChatGroup group;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @Setter
    @Column(name = "joined_at")
    private LocalDateTime joinedAt = LocalDateTime.now();

    public ChatMember() {}

    public ChatMember(ChatGroup group, User user, Role role) {
        this.id = new ChatMemberId(group.getId(), user.getId());
        this.group = group;
        this.user = user;
        this.role = role;
        this.joinedAt = LocalDateTime.now();
    }
}
