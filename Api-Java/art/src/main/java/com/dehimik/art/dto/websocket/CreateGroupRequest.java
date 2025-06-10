package com.dehimik.art.dto.websocket;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateGroupRequest {
    @NotNull(message = "The name must not be blank")
    private String name;
    @NotNull(message = "The memberIds must not be blank")
    private List<Long> memberIds;
}
