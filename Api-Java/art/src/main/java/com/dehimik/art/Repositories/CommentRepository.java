package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.*;
import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Long>{
    List<Comment> findByPostId(Long postId);
}
