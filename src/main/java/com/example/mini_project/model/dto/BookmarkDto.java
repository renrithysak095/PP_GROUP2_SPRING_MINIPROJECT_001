package com.example.mini_project.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;
    private ArticleDto article;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;

    public BookmarkDto(ArticleDto article) {
        this.article = article;
    }
}
