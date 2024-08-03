package com.company.coment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public String create(@RequestBody ReqCommentCreate req) {
        return commentService.createComment(req);
    }

    @GetMapping("/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable("postId") Integer postId) {
        log.info("Comments by post id {}", postId);
        return commentService.getByPostId(postId);
    }
}
