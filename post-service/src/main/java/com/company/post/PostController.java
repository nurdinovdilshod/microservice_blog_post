package com.company.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ReqPostCreate req) {
        log.info("Post create request {}", req);
        return ResponseEntity.ok(postService.create(req));
    }

    @GetMapping("/{id}")
    public ResPost getById(@PathVariable Integer id) {
        log.info("Post by id {}", id);
        return postService.getPostById(id);
    }

    @GetMapping("")
    public List<ResPost> getAll() {
        return postService.getAll();
    }
}
