package com.dehimik.art.Repositories;

import com.dehimik.art.Entities.*;
import java.util.List;

public interface PostTagRepository extends BaseRepository<PostTag, PostTagId>{
    List<PostTag> findByPostId(PostTagId postId);
}
