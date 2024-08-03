package com.company.comment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("${comment.service.base.url}")
public interface CommentClient {

    @GetMapping("/{postId}")
    List<ResComment> getAllCommentsByPostId(@PathVariable("postId") Integer postId);
}
