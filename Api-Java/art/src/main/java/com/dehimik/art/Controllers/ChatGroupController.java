package com.dehimik.art.Controllers;

import com.dehimik.art.Entities.*;
import com.dehimik.art.Repositories.ChatGroupRepository;
import com.dehimik.art.Repositories.ChatMemberRepository;
import com.dehimik.art.Repositories.ChatMessageRepository;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.websocket.AddMemberRequest;
import com.dehimik.art.dto.websocket.ChatMessageResponse;
import com.dehimik.art.dto.websocket.CreateGroupRequest;
import com.dehimik.art.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ChatGroupController {

    private final ChatGroupRepository groupRepo;
    private final ChatMemberRepository memberRepo;
    private final UserRepository userRepo;
    private final ChatMessageRepository messageRepo;

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest request, Principal principal) {
        User creator = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

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

        return ResponseEntity.ok(Map.of("groupId", group.getId()));
    }

    @GetMapping("/{groupId}/messages")
    public ResponseEntity<?> getGroupMessages(@PathVariable Long groupId, Principal principal) {
        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatGroup group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        boolean isMember = memberRepo.existsByGroupAndUser(group, user);
        if (!isMember) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not a group member");
        }

        List<ChatMessage> messages = messageRepo.findByGroupOrderByCreatedAtAsc(group);

        List<ChatMessageResponse> response = messages.stream()
                .map(msg -> new ChatMessageResponse(
                        groupId,
                        msg.getSender().getUsername(),
                        msg.getContent(),
                        msg.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<?> addMember(@PathVariable Long groupId,
                                       @RequestBody AddMemberRequest request,
                                       Principal principal) {
        User currentUser = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatGroup group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        ChatMemberId currentId = new ChatMemberId(groupId, currentUser.getId());
        ChatMember currentMember = memberRepo.findById(currentId)
                .orElseThrow(() -> new RuntimeException("Not a group member"));

        if (!"OWNER".equalsIgnoreCase(String.valueOf(currentMember.getRole()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only group owner can add members");
        }

        User newUser = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User to add not found"));

        if (memberRepo.existsByGroupAndUser(group, newUser)) {
            return ResponseEntity.badRequest().body("User is already a member");
        }

        ChatMember newMember = new ChatMember(
                group,
                newUser,
                request.getRole() != null ? request.getRole() : Role.MEMBER
        );
        memberRepo.save(newMember);

        return ResponseEntity.ok("User added to group");
    }

}
