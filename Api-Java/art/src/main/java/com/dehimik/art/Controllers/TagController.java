package com.dehimik.art.Controllers;

import com.dehimik.art.dto.*;
import com.dehimik.art.dto.post.TagDto;
import com.dehimik.art.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService svc;

    @PostMapping
    public TagDto create(@RequestBody TagDto dto) {
        return svc.create(dto);
    }
}
