package com.example.mini_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

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

    @Column(name = "article_description")
    private String description;

    @Column(name = "article_publish")
    private Boolean published;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    List<Category> categories;

    @OneToMany(mappedBy = "article")
    private List<Bookmark> bookmarks;

}
