package com.example.mini_project.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private UUID id;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDto user;
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentDto> comments = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryDto> categories;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BookmarkDto> bookmarks;
    private Boolean published;

    public ArticleDto(UUID id, String description, Boolean published, UserDto user, List<CommentDto> comments, List<CategoryDto> categories) {
        this.id = id;
        this.description = description;
        this.published = published;
        this.user = user;
        this.comments = comments;
        this.categories = categories;
    }
}
