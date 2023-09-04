package com.example.mini_project.service;

import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.BookmarkDto;
import com.example.mini_project.model.request.BookmarkRequest;
import com.example.mini_project.model.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface BookmarkService {

    ArticleDto saveBookmarkArticle(UUID id, BookmarkRequest bookmarkRequest);

    PageResponse<List<ArticleDto>> getBookmarkArticleByUserId(UUID id, Integer page, Integer size);

    void deleteBookmark(UUID id, UUID articleId);
}
