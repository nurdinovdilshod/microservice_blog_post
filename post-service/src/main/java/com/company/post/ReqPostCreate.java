package com.company.post;

import com.company.comment.ResComment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReqPostCreate {

    private String title;
    private String body;

}
