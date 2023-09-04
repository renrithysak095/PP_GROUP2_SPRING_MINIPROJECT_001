package com.example.mini_project.controller;

import com.example.mini_project.model.request.BookmarkRequest;
import com.example.mini_project.service.BookmarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/bookmarks")
@Tag(name = "Bookmarks")
@AllArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("user/{id}")
    public ResponseEntity<?> getBookmarkArticle(@PathVariable UUID id, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize){
        return ResponseEntity.ok().body(bookmarkService.getBookmarkArticle(id, pageNo, pageSize));
    }

    @PostMapping("user/{id}")
    public ResponseEntity<?> bookmarkOnArticle(@RequestBody BookmarkRequest bookmarkRequest, @PathVariable UUID id){
        return ResponseEntity.ok().body(bookmarkService.bookmarkOnArticle(bookmarkRequest, id));
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> removeBookmarkFromArticle(@PathVariable UUID id, @RequestParam UUID articleId){
        return ResponseEntity.ok().body(bookmarkService.removeBookmarkFromArticle(id, articleId));
    }
}
