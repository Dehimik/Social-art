package com.dehimik.art.dto.project;
import com.dehimik.art.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectMemberDto {
    private Long userId;
    private String username;
    private Role role;
}
