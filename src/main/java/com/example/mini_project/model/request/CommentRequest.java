package com.example.mini_project.model.request;

import com.example.mini_project.model.Article;
import com.example.mini_project.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String caption;

    public Comment toEntity(Article article){
        return new Comment(null, this.caption, article);
    }
}
