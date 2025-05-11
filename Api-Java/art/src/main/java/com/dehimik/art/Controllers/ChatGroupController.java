package com.dehimik.art.Controllers;

import com.dehimik.art.Entities.ChatGroup;
import com.dehimik.art.Entities.ChatMember;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.ChatGroupRepository;
import com.dehimik.art.Repositories.ChatMemberRepository;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.websocket.CreateGroupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ChatGroupController {

    private final ChatGroupRepository groupRepo;
    private final ChatMemberRepository memberRepo;
    private final UserRepository userRepo;

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest request, Principal principal) {
        User creator = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatGroup group = new ChatGroup();
        group.setName(request.getName());
        group.setCreatedBy(creator);
        group.setCreatedAt(LocalDateTime.now());
        group = groupRepo.save(group);

        memberRepo.save(new ChatMember(group, creator, "OWNER"));

        for (Long userId : request.getMemberIds()) {
            if (!userId.equals(creator.getId())) {
                ChatGroup finalGroup = group;
                userRepo.findById(userId).ifPresent(user ->
                        memberRepo.save(new ChatMember(finalGroup, user, "MEMBER"))
                );
            }
        }

        return ResponseEntity.ok(Map.of("groupId", group.getId()));
    }
}
