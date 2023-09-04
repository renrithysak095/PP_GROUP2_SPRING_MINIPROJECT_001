package com.example.mini_project.model.dto;

import com.example.mini_project.model.Bookmark;
import com.example.mini_project.model.Category;
import com.example.mini_project.model.Comment;
import com.example.mini_project.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private UUID id;
    private String description;
    private Boolean published;
    private User user;
    private List<Comment> comments;
    List<Category> categories;
    private List<Bookmark> bookmarks;

}
