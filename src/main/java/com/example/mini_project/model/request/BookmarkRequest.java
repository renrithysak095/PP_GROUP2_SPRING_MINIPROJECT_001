package com.example.mini_project.model.request;

import com.example.mini_project.model.Article;
import com.example.mini_project.model.Bookmark;
import com.example.mini_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {

    private UUID articleId;

    public Bookmark toEntity(Article article, User user){
        return new Bookmark(null, article, user);
    }
}
