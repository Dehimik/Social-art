package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.Post;
import com.dehimik.art.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    //Optional<User> findByUsername(String username);
}
