package com.dehimik.art.dto.websocket;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageDTO {
    @NotNull(message = "The message must not be blank")
    private String content;
}
