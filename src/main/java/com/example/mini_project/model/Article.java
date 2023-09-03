package com.example.mini_project.model;

import com.example.mini_project.model.dto.ArticleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "article_id")
    private UUID id;

    @Column(name = "article_title")
    private String title;

    @Column(name = "article_description")
    private String description;

    @Column(name = "article_publish")
    private Boolean published;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    List<Category> categories;

    @OneToMany(mappedBy = "article")
    private List<Bookmark> bookmarks;

    public Article(UUID id, String title, String description, Boolean published, User user, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.published = published;
        this.user = user;
        this.categories = categories;
    }

    public ArticleDto toDto(){
        return new ArticleDto(this.id, this.description, this.published, this.user.toDto(), this.comments.stream().map(Comment::toDto).collect(Collectors.toList()), this.categories.stream().map(Category::toDto).collect(Collectors.toList()));
    }
}
