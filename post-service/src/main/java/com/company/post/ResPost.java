package com.company.post;

import com.company.comment.ResComment;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResPost {
    private Integer id;
    private String title;
    private String body;
    private List<ResComment>comments;
}
