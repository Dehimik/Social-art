package com.dehimik.art.dto.project;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectMemberDto {
    private Long userId;
    private String username;
    private String role;
}
