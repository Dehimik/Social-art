package com.dehimik.art.services;

import com.dehimik.art.Entities.*;
import com.dehimik.art.Repositories.*;
import com.dehimik.art.dto.post.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository repo;
    public LikeDto like(LikeDto d) {
        PostLike e = new PostLike();
        e.setUser(new User(d.getUserId()));
        e.setPost(new Post(d.getPostId()));
        PostLike saved = repo.save(e);
        return new LikeDto(saved.getId(),
                saved.getUser().getId(),
                saved.getPost().getId());
    }
    public void unlike(Long userId, Long postId) {
        repo.findByUserIdAndPostId(userId, postId)
                .ifPresent(l -> repo.deleteById(l.getId()));
    }
    public long count(Long postId) { return repo.countByPostId(postId); }
}
