package com.example.mini_project.service;

import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.CommentDto;
import com.example.mini_project.model.request.ArticleRequest;
import com.example.mini_project.model.request.CommentRequest;
import com.example.mini_project.model.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticleDto createArticle(ArticleRequest articleRequest);

    PageResponse<List<ArticleDto>> getAllArticles(Integer pageNo, Integer pageSize);

    CommentDto postComment(CommentRequest commentRequest, UUID id);

    List<CommentDto> getAllCommentsInArticle(UUID id);

    ArticleDto getArticle(UUID id);

    void deleteArticle(UUID id);

    ArticleDto updateArticle(ArticleRequest articleRequest, UUID id);

    PageResponse<List<ArticleDto>> getAllArticlesIsPublished(Integer pageNo, Integer pageSize);
}