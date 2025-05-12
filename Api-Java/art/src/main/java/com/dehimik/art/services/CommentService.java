package com.dehimik.art.services;

import com.dehimik.art.Entities.*;
import com.dehimik.art.dto.*;
import com.dehimik.art.Repositories.*;
import com.dehimik.art.dto.post.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repo;
    public CommentDto create(CommentDto d) {
        Comment e = new Comment();
        e.setPost(new Post(d.getPostId()));
        e.setUser(new User(d.getUserId()));
        e.setContent(d.getContent());
        Comment saved = repo.save(e);
        return new CommentDto(saved.getId(), saved.getPost().getId(),
                saved.getUser().getId(), saved.getContent(),
                saved.getCreatedAt());
    }
    public List<CommentDto> listByPost(Long postId) {
        return repo.findByPostId(postId).stream().map(c ->
                new CommentDto(c.getId(), c.getPost().getId(),
                        c.getUser().getId(), c.getContent(),
                        c.getCreatedAt())
        ).collect(Collectors.toList());
    }
    public void delete(Long id) { repo.deleteById(id); }
}
