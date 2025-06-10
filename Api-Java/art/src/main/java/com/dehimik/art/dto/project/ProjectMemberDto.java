package com.dehimik.art.dto.project;
import com.dehimik.art.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectMemberDto {
    @NotNull
    private Long userId;
    @NotBlank
    private String username;
    @NotNull
    private Role role;
}
