package com.example.mini_project.controller;

import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.request.BookmarkRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/bookmarks/user")
@Tag(name = "Bookmarks")
@AllArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("{id}")
    public ResponseEntity <?> saveBookmarkArticle(@PathVariable UUID id, @RequestBody BookmarkRequest bookmarkRequest){
        ApiResponse<?> response = ApiResponse.<ArticleDto>builder()
                .message("Save article successfully")
                .payload(bookmarkService.saveBookmarkArticle(id,bookmarkRequest))
                .status(HttpStatus.CREATED).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Bookmark Article")
    public ResponseEntity<?> getBookmarkArticleByUserId(@PathVariable UUID id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size ){
        return  ResponseEntity.ok().body(bookmarkService.getBookmarkArticleByUserId(id, page, size));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBookmark(@PathVariable UUID id,@PathVariable UUID article_id){
        bookmarkService.deleteBookmark(id,article_id);
        ApiResponse<?> response =ApiResponse.<ArticleDto>builder()
                .message("Delete bookmark successfully")
                .payload(null)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(response);
    }

}
