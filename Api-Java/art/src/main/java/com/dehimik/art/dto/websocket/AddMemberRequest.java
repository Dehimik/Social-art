package com.dehimik.art.dto.websocket;

import com.dehimik.art.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddMemberRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Role role;
}
