package com.dehimik.art.Controllers;

import com.dehimik.art.dto.post.*;
import com.dehimik.art.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/tags")
@RequiredArgsConstructor @Validated
public class TagController {
    private final TagService svc;

    @PostMapping
    public ResponseEntity<TagResponse> create(
            @Valid @RequestBody TagRequest req
    ) {
        return ResponseEntity.status(201)
                .body(svc.create(req));
    }
}
