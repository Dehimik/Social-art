package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.*;
import java.util.Optional;

public interface PostLikeRepository extends BaseRepository<PostLike, Long>{
    Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);
    long countByPostId(Long postId);
}
