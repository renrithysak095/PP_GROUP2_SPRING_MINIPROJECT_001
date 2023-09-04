package com.example.mini_project.model.dto;

import com.example.mini_project.model.Article;
import com.example.mini_project.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDto {

    private UUID id;
    private ArticleDto article;
    private UserDto user;

    public BookmarkDto(ArticleDto article) {
        this.article = article;
    }

}
