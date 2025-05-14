package com.dehimik.art.services;

import com.dehimik.art.dto.post.*;
import com.dehimik.art.Entities.*;
import com.dehimik.art.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository likeRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Transactional
    public LikeResponse like(Long postId, LikeRequest req) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));
        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + req.getUserId()));

        likeRepo.findByUserIdAndPostId(user.getId(), post.getId())
                .ifPresent(l -> { throw new IllegalStateException("Already liked"); });

        PostLike pl = new PostLike();
        pl.setPost(post); pl.setUser(user);
        PostLike saved = likeRepo.save(pl);

        return new LikeResponse(saved.getId(), user.getId(), post.getId());
    }

    @Transactional
    public void unlike(Long postId, Long userId) {
        PostLike exist = likeRepo.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));
        likeRepo.delete(exist);
    }

    @Transactional(readOnly = true)
    public long count(Long postId) {
        if (!postRepo.existsById(postId))
            throw new EntityNotFoundException("Post not found: " + postId);
        return likeRepo.countByPostId(postId);
    }
}
