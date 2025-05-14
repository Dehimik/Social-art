package com.dehimik.art.services;

import com.dehimik.art.Entities.Post;
import com.dehimik.art.Entities.Tag;
import com.dehimik.art.Entities.User;
import com.dehimik.art.Repositories.BaseRepository;
import com.dehimik.art.Repositories.PostRepository;
import com.dehimik.art.Repositories.TagRepository;
import com.dehimik.art.Repositories.UserRepository;
import com.dehimik.art.dto.post.PostRequestDto;
import com.dehimik.art.dto.post.PostResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long>{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    private PostResponseDto convertToDto(Post post){
        return new PostResponseDto(
                post.getMediaUrl(),
                post.getPostText(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public Post createPost(PostRequestDto dto, Long userId) {
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

    public List<PostResponseDto> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto dto, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        /*if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized update attempt");
        }*/

        if (dto.getPostText() != null) {
            post.setPostText(dto.getPostText());
        }

        if (dto.getMediaUrl() != null) {
            post.setMediaUrl(dto.getMediaUrl());
        }

        post.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);
        return convertToDto(updatedPost);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this post");
        }

        postRepository.delete(post);
    }

    @Transactional
    public void addTagToPost(Long postId, Long tagId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + tagId));

        post.getTags().add(tag);
        postRepository.save(post);
    }

    @Transactional
    public void removeTagFromPost(Long postId, Long tagId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));
        post.getTags().removeIf(t -> t.getId().equals(tagId));
        postRepository.save(post);
    }

    @Override
    protected BaseRepository<Post, Long> getRepository() {
        return postRepository;
    }
}
