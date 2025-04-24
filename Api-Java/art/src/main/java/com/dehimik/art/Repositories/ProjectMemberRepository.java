package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.ProjectMember;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends BaseRepository<ProjectMember, Long>{
    List<ProjectMember> findByProjectId(Long projectId);
    Optional<ProjectMember> findByUserIdAndProjectId(Long userId, Long projectId);
}
