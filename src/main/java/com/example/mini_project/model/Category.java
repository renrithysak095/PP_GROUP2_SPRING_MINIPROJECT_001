package com.example.mini_project.model;

import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID id;

    @Column(name = "category_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    private List<Article> articles;

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto toDto() {
        return new CategoryDto(this.id, this.name);
    }

}
