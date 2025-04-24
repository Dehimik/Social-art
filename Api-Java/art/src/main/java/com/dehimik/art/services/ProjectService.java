package com.dehimik.art.services;

import com.dehimik.art.Entities.Project;
import com.dehimik.art.Entities.ProjectMember;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.ProjectMemberRepository;
import com.dehimik.art.Repositories.ProjectRepository;
import com.dehimik.art.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    public Project createProject(String name, String description, String mediaUrl) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setMediaUrl(mediaUrl);
        project.setCreatedAt(LocalDateTime.now());

        return projectRepository.save(project);
    }

    public ProjectMember addMemberToProject(Long projectId, Long userId, String role) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(project);
        projectMember.setUser(user);
        projectMember.setRole(role);
        projectMember.setJoinedAt(LocalDateTime.now());

        return projectMemberRepository.save(projectMember);
    }

    public List<ProjectMember> getMembersByProject(Long projectId) {
        return projectMemberRepository.findByProjectId(projectId);
    }

    public String getRoleForUserInProject(Long projectId, Long userId) {
        return projectMemberRepository.findByUserIdAndProjectId(userId, projectId)
                .map(ProjectMember::getRole)
                .orElseThrow(() -> new RuntimeException("User is not member of this project"));
    }

    public ProjectMember updateRoleInProject(Long projectId, Long userId, String newRole) {
        ProjectMember member = projectMemberRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        member.setRole(newRole);
        return projectMemberRepository.save(member);
    }

    public void removeMemberFromProject(Long projectId, Long userId) {
        ProjectMember member = projectMemberRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new RuntimeException("Project member not found"));

        projectMemberRepository.delete(member);
    }
}
