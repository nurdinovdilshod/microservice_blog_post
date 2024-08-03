package com.company.post;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("${post.service.base.url}")
public interface PostClient {
    @GetMapping("/{id}")
    ResPost getById(@PathVariable("id") Integer id);
}
