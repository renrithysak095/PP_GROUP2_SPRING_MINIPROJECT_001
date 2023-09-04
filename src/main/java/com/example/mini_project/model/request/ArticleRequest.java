package com.example.mini_project.model.request;

import com.example.mini_project.model.Article;
import com.example.mini_project.model.Category;
import com.example.mini_project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {

        private String title;
        private String description;
        private List<CategoryRequest> categories;
        private UUID userId;
        private Boolean published = false;

        public Article toEntity(User user, List<Category> categories){
              return new Article(null, this.title, this.description, this.published, user, categories);
        }

        public Article toEntity(UUID id, User user, List<Category> categories){
                return new Article(id, this.title, this.description, this.published, user, categories);
        }
}
