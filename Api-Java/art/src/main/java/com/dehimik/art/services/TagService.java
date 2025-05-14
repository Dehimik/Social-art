package com.dehimik.art.services;

import com.dehimik.art.dto.post.*;
import com.dehimik.art.Entities.Tag;
import com.dehimik.art.Repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepo;

    @Transactional
    public TagResponse create(TagRequest req) {
        Tag t = tagRepo.findByName(req.getName())
                .orElseGet(() -> tagRepo.save(new Tag(null, req.getName(), null)));
        return new TagResponse(t.getId(), t.getName());
    }
}
