package com.example.mini_project.controller;

import com.example.mini_project.model.request.ArticleRequest;
import com.example.mini_project.model.request.CommentRequest;
import com.example.mini_project.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/articles")
@Tag(name = "Articles")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<?> createArticle(@RequestBody ArticleRequest articleRequest){
        return ResponseEntity.ok().body(articleService.createArticle(articleRequest));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllArticles(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(articleService.getAllArticles(pageNo, pageSize));
    }

    @GetMapping("{id}")
    public  ResponseEntity<?> getArticle(@PathVariable UUID id){
        return ResponseEntity.ok().body(articleService.getArticle(id));
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<?> deleteArticle(@PathVariable UUID id){
        return ResponseEntity.ok().body(articleService.deleteArticle(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleRequest articleRequest, @PathVariable UUID id){
        return ResponseEntity.ok().body(articleService.updateArticle(articleRequest, id));
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<?> postComment(@RequestBody CommentRequest commentRequest, @PathVariable UUID id){
        return ResponseEntity.ok().body(articleService.postComment(commentRequest, id));
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<?> getAllCommentsInArticle(@PathVariable UUID id){
        return ResponseEntity.ok().body(articleService.getAllCommentsInArticle(id));
    }

    @GetMapping("isPublished")
    public ResponseEntity<?> getAllArticlesIsPublished(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(articleService.getAllArticlesIsPublished(pageNo, pageSize));
    }
}
