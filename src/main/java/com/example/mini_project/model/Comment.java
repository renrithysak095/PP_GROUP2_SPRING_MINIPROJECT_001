package com.example.mini_project.model;

import com.example.mini_project.model.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id")
    private UUID id;

    @Column(name = "comment_caption")
    private String caption;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public CommentDto toDto(){
        return new CommentDto(this.id, this.caption);
    }
}
