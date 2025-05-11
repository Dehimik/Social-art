package com.dehimik.art.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateGroupRequest {
    private String name;
    private List<Long> memberIds;
}
