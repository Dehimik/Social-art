package com.dehimik.art.services;

import com.dehimik.art.Entities.Post;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.BaseRepository;
import com.dehimik.art.Repositories.PostRepository;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.post.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long>{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(PostDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(user);
        post.setMediaUrl(dto.getMediaUrl());
        post.setPostText(dto.getPostText());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    protected BaseRepository<Post, Long> getRepository() {
        return postRepository;
    }
}
