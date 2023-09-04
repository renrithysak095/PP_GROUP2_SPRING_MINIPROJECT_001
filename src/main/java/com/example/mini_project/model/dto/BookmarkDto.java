package com.example.mini_project.model.dto;

import com.example.mini_project.model.Article;
import com.example.mini_project.model.User;
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

    private UUID id;
    private ArticleDto articleDto;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;



}
