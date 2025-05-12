package com.dehimik.art.services;

import com.dehimik.art.Entities.*;
import com.dehimik.art.dto.*;
import com.dehimik.art.Repositories.*;
import com.dehimik.art.dto.post.PostTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class PostTagService {
    private final PostTagRepository repo;
    public void add(PostTagDto d) {
        PostTag e = new PostTag(d.getPostId(), d.getTagId(),
                new Post(d.getPostId()), new Tag(d.getTagId(), null));
        repo.save(e);
    }
    public void remove(PostTagDto d) {
        PostTagId id = new PostTagId(d.getPostId(), d.getTagId());
        repo.deleteById(id);
    }
}
