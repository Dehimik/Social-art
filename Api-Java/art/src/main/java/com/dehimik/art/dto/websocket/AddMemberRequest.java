package com.dehimik.art.dto.websocket;

import com.dehimik.art.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddMemberRequest {
    private Long userId;
    private Role role;
}
