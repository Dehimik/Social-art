package com.dehimik.art.Controllers;

import com.dehimik.art.Entities.ChatGroup;
import com.dehimik.art.Entities.ChatMessage;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.ChatGroupRepository;
import com.dehimik.art.Repositories.ChatMemberRepository;
import com.dehimik.art.Repositories.ChatMessageRepository;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.websocket.ChatMessageDTO;
import com.dehimik.art.dto.websocket.ChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatWebsocketController {
    private final ChatGroupRepository groupRepo;
    private final ChatMemberRepository memberRepo;
    private final ChatMessageRepository messageRepo;
    private final UserRepository userRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send.{groupId}")
    public void sendMessage(@DestinationVariable Long groupId,
                            @Payload ChatMessageDTO dto,
                            Principal principal) {

        User sender = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatGroup group = groupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!memberRepo.existsByGroupAndUser(group, sender)) {
            throw new AccessDeniedException("You are not a member of this group");
        }

        ChatMessage msg = new ChatMessage();
        msg.setGroup(group);
        msg.setSender(sender);
        msg.setContent(dto.getContent());
        msg.setCreatedAt(LocalDateTime.now());
        messageRepo.save(msg);

        messagingTemplate.convertAndSend("/topic/group." + groupId,
                new ChatMessageResponse(groupId, sender.getUsername(), msg.getContent(), msg.getCreatedAt()));
    }
}
