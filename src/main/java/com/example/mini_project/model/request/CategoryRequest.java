package com.example.mini_project.model.request;

import com.example.mini_project.model.Article;
import com.example.mini_project.model.Category;
import com.example.mini_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private String name;
    public Category toEntity(String name){
        return new Category(null, this.name);
    }
    public Category toEntity(UUID id,String name){
        return new Category(id, this.name);
    }

}
