package com.company.post;

import com.company.comment.CommentClient;
import com.company.comment.ResComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentClient commentClient;

    public String create(ReqPostCreate req) {
        Post post = Post.builder()
                .title(req.getTitle())
                .body(req.getBody())
                .build();
        postRepository.save(post);

        return "Post successfully saved";
    }

    public ResPost getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post Not Found ☠"));
        List<ResComment> commentsByPostId = commentClient.getAllCommentsByPostId(post.getId());
        return mapToResPost(post,commentsByPostId);
    }

    public List<ResPost> getAll() {
        return postRepository.findAll().stream()
                .map(p->mapToResPost(p,null))
                .collect(Collectors.toList());
    }

    private ResPost mapToResPost(Post post, List<ResComment> comments) {
       return ResPost.builder()
                .id(post.getId())
                .body(post.getBody())
                .title(post.getTitle())
                .comments(comments)
                .build();
    }
}
