package com.example.mini_project.model.dto;

import com.example.mini_project.enumeration.Role;
import com.example.mini_project.model.Bookmark;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;
    private String name;
    private String role;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BookmarkDto> bookmarks;

    public UserDto(UUID id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
