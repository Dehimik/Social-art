package com.dehimik.art.services;

import com.dehimik.art.Entities.*;
import com.dehimik.art.dto.post.*;
import com.dehimik.art.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Transactional
    public CommentResponse create(Long postId, CommentRequest req) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));
        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + req.getUserId()));

        Comment c = new Comment();
        c.setPost(post);
        c.setUser(user);
        c.setContent(req.getContent());
        Comment saved = commentRepo.save(c);

        return new CommentResponse(
                saved.getId(), post.getId(), user.getId(),
                saved.getContent(), saved.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> listByPost(Long postId) {
        return commentRepo.findByPostId(postId).stream()
                .map(c -> new CommentResponse(
                        c.getId(), c.getPost().getId(),
                        c.getUser().getId(), c.getContent(),
                        c.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!commentRepo.existsById(id)) {
            throw new EntityNotFoundException("Comment not found: " + id);
        }
        commentRepo.deleteById(id);
    }
}
