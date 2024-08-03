package com.company.coment;

import com.company.post.PostClient;
import com.company.post.ResPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostClient postClient;

    public String createComment(ReqCommentCreate req) {
        Integer postId = req.getPostId();
        ResPost postById = postClient.getById(postId);
//        if (postById == null) {
//            throw new RuntimeException("Post not found");
//        }
        Comment comment = Comment.builder()
                .message(req.getMessage())
                .postId(postId)
                .build();
        commentRepository.save(comment);
        return "Comment for post '%s' created".formatted(postById.getTitle());
    }

    public List<Comment> getByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }
}
