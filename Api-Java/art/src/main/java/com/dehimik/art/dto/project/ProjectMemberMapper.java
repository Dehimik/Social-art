package com.dehimik.art.dto.project;

import com.dehimik.art.Entities.ProjectMember;

public class ProjectMemberMapper {
    public static ProjectMemberDto toDto(ProjectMember member) {
        return new ProjectMemberDto(
                member.getUser().getId(),
                member.getUser().getUsername(),
                member.getRole()
        );
    }
}
