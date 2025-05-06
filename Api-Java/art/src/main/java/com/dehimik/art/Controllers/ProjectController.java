package com.dehimik.art.Controllers;

import com.dehimik.art.Entities.Project;
import com.dehimik.art.Entities.ProjectMember;
import com.dehimik.art.dto.project.ProjectMemberDto;
import com.dehimik.art.services.ProjectService;
import static com.dehimik.art.dto.project.ProjectMemberMapper.toDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestParam String name,
                                                 @RequestParam String description,
                                                 @RequestParam(required = false) String mediaUrl) {
        Project project = projectService.createProject(name, description, mediaUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @PostMapping("/{projectId}/members")
    public ResponseEntity<ProjectMemberDto> addMember(@PathVariable Long projectId, @RequestParam Long userId, @RequestParam String role) {
        ProjectMember member = projectService.addMemberToProject(projectId, userId, role);
        return ResponseEntity.ok(toDto(member));
    }

    @GetMapping("/{projectId}/members")
    public List<ProjectMember> getMembers(@PathVariable Long projectId) {
        return projectService.getMembersByProject(projectId);
    }

    @GetMapping("/{projectId}/members/{userId}/role")
    public String getRole(@PathVariable Long projectId, @PathVariable Long userId) {
        return projectService.getRoleForUserInProject(projectId, userId);
    }

    @PutMapping("/{projectId}/members/{userId}/role")
    public ResponseEntity<ProjectMemberDto> updateRole(@PathVariable Long projectId, @PathVariable Long userId, @RequestParam String newRole) {
        ProjectMember updatedMember = projectService.updateRoleInProject(projectId, userId, newRole);
        return ResponseEntity.ok(toDto(updatedMember));
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.removeMemberFromProject(projectId, userId);
        return ResponseEntity.noContent().build();
    }
}
