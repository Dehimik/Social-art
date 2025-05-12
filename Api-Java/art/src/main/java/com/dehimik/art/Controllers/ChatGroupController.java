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
import com.dehimik.art.services.ChatGroupService;
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
@RequestMapping("/groups")
@RequiredArgsConstructor
public class ChatGroupController {
    private final ChatGroupService chatGroupService;

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest request, String creatorName/*Principal principal*/) {

        Long groupId = chatGroupService.createGroup(request, creatorName/*principal.getName()*/);
        return ResponseEntity.ok(Map.of("groupId", groupId));
    }

    @GetMapping("/{groupId}/messages")
    public ResponseEntity<?> getGroupMessages(@PathVariable Long groupId, String creatorName/*Principal principal*/) {
        var messages = chatGroupService.getMessages(groupId, creatorName/*principal.getName()*/);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<?> addMember(@PathVariable Long groupId,
                                       @RequestBody AddMemberRequest request,
                                       @RequestParam String creatorName/*,
                                       Principal principal*/) {
        chatGroupService.addMember(groupId, creatorName, request);
        return ResponseEntity.ok("Member added");
    }
}
