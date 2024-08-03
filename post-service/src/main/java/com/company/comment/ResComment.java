package com.company.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResComment {
    private Integer id;
    private String message;
    private Integer postId;
}
