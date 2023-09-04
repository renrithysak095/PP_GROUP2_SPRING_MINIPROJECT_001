package com.example.mini_project.service;

import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.BookmarkDto;
import com.example.mini_project.model.request.BookmarkRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.model.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface BookmarkService {
    ApiResponse<ArticleDto> bookmarkOnArticle(BookmarkRequest bookmarkRequest, UUID id);

    ApiResponse<BookmarkDto> removeBookmarkFromArticle(UUID id, UUID articleId);

   PageResponse<List<ArticleDto>> getBookmarkArticle(UUID id, Integer pageNo, Integer pageSize);
}
