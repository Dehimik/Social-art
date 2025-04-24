package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.Project;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Long> {
    Optional<Project> findById(Long id);
}
