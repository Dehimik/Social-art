package com.dehimik.art.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatMessageResponse {
    @Getter
    private Long groupId;

    @Getter
    private String sender;

    @Getter
    private String content;

    @Getter
    private LocalDateTime createdAt;

}
