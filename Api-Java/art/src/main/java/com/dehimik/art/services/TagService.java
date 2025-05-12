package com.dehimik.art.services;

import com.dehimik.art.Entities.*;
import com.dehimik.art.Repositories.*;
import com.dehimik.art.dto.post.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class TagService {
    private final TagRepository repo;
    public TagDto create(TagDto d) {
        Tag e = repo.findByName(d.getName())
                .orElseGet(() -> repo.save(new Tag(null, d.getName())));
        return new TagDto(e.getId(), e.getName());
    }
}
