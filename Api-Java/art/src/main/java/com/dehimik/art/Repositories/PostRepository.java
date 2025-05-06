package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.Post;
import com.dehimik.art.Entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {
    List<Post> findByUser(User user);
}
