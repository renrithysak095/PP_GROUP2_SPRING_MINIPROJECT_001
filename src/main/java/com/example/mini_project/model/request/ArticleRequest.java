package com.example.mini_project.model.request;

import com.example.mini_project.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {


        private String title;
        private String description;
        private List<CategoryRequest> categories;
        private UUID teacherId;
        private Boolean published;


}
