package com.example.mini_project.controller;

import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.CommentDto;
import com.example.mini_project.model.request.ArticleRequest;
import com.example.mini_project.model.request.CommentRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/articles")
@Tag(name = "Articles")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<?> createArticle(@RequestBody ArticleRequest articleRequest){
        ApiResponse<ArticleDto> response = ApiResponse.<ArticleDto>builder()
                .message("successfully create article")
                .payload(articleService.createArticle(articleRequest))
                .status(HttpStatus.CREATED)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllArticles(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(articleService.getAllArticles(pageNo, pageSize));
    }

    @GetMapping("{id}")
    public  ResponseEntity<?> getArticle(@PathVariable UUID id){
        ApiResponse<ArticleDto> response = ApiResponse.<ArticleDto>builder()
                .message("comment with article id: " + id)
                .payload(articleService.getArticle(id))
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<?> deleteArticle(@PathVariable UUID id){
        articleService.deleteArticle(id);
        ApiResponse<ArticleDto> response = ApiResponse.<ArticleDto>builder()
                .message("deleted successfully")
                .payload(null)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleRequest articleRequest, @PathVariable UUID id){
        ApiResponse<ArticleDto> response = ApiResponse.<ArticleDto>builder()
                .message("successfully update article")
                .payload(articleService.updateArticle(articleRequest, id))
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("{id}/comments")
    public ResponseEntity<?> postComment(@RequestBody CommentRequest commentRequest, @PathVariable UUID id){
        ApiResponse<CommentDto> response = ApiResponse.<CommentDto>builder()
                .message("comment with article id: " + id)
                .payload(articleService.postComment(commentRequest, id))
                .status(HttpStatus.CREATED)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<?> getAllCommentsInArticle(@PathVariable UUID id){
        ApiResponse<List<CommentDto>> response = ApiResponse.<List<CommentDto>>builder()
                .message("success fetch comment by article id: " + id)
                .payload(articleService.getAllCommentsInArticle(id))
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("isPublished")
    public ResponseEntity<?> getAllArticlesIsPublished(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(articleService.getAllArticlesIsPublished(pageNo, pageSize));
    }
}
