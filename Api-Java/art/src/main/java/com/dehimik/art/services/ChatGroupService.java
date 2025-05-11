package com.dehimik.art.services;

import com.dehimik.art.Entities.ChatGroup;
import com.dehimik.art.Entities.ChatMember;
import com.dehimik.art.Entities.ChatMemberId;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.*;
import com.dehimik.art.dto.websocket.AddMemberRequest;
import com.dehimik.art.dto.websocket.ChatMessageResponse;
import com.dehimik.art.dto.websocket.CreateGroupRequest;
import com.dehimik.art.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGroupService extends BaseService<ChatGroup, Long>{
    private final ChatGroupRepository groupRepo;
    private final ChatMemberRepository memberRepo;
    private final ChatMessageRepository messageRepo;
    private final UserRepository userRepo;

    public Long createGroup(CreateGroupRequest request, String creatorUsername) {
        User creator = getUser(creatorUsername);

        ChatGroup group = new ChatGroup();
        group.setName(request.getName());
        group.setCreatedBy(creator);
        group.setCreatedAt(LocalDateTime.now());
        group = groupRepo.save(group);

        memberRepo.save(new ChatMember(group, creator, Role.ADMIN));

        for (Long userId : request.getMemberIds()) {
            if (!userId.equals(creator.getId())) {
                ChatGroup finalGroup = group;
                userRepo.findById(userId).ifPresent(user ->
                        memberRepo.save(new ChatMember(finalGroup, user, Role.MEMBER))
                );
            }
        }

        return group.getId();
    }

    public List<ChatMessageResponse> getMessages(Long groupId, String username) {
        User user = getUser(username);
        ChatGroup group = getGroup(groupId);

        if (!memberRepo.existsByGroupAndUser(group, user)) {
            throw new AccessDeniedException("Not a group member");
        }

        return messageRepo.findByGroupOrderByCreatedAtAsc(group).stream()
                .map(msg -> new ChatMessageResponse(
                        group.getId(),
                        msg.getSender().getUsername(),
                        msg.getContent(),
                        msg.getCreatedAt()
                ))
                .toList();
    }

    public void addMember(Long groupId, String adminUsername, AddMemberRequest request) {
        User admin = getUser(adminUsername);
        ChatGroup group = getGroup(groupId);

        ChatMemberId adminId = new ChatMemberId(groupId, admin.getId());
        ChatMember adminMember = memberRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Not a group member"));

        if (!"OWNER".equalsIgnoreCase(String.valueOf(adminMember.getRole()))) {
            throw new AccessDeniedException("Only owner can add members");
        }

        User newUser = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (memberRepo.existsByGroupAndUser(group, newUser)) {
            throw new IllegalArgumentException("User already in group");
        }

        memberRepo.save(new ChatMember(group, newUser, request.getRole() != null ? request.getRole() : Role.MEMBER));
    }

    private User getUser(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private ChatGroup getGroup(Long groupId) {
        return groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    @Override
    protected BaseRepository<ChatGroup, Long> getRepository() {
        return groupRepo;
    }
}
