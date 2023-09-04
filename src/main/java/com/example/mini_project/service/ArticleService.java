package com.example.mini_project.service;

import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.CommentDto;
import com.example.mini_project.model.request.ArticleRequest;
import com.example.mini_project.model.request.CommentRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.model.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ApiResponse<ArticleDto> createArticle(ArticleRequest articleRequest);

    PageResponse<List<ArticleDto>> getAllArticles(Integer pageNo, Integer pageSize);

    ApiResponse<CommentDto> postComment(CommentRequest commentRequest, UUID id);

    ApiResponse<List<CommentDto>> getAllCommentsInArticle(UUID id);

    ApiResponse<ArticleDto> getArticle(UUID id);

    ApiResponse<ArticleDto> deleteArticle(UUID id);

    ApiResponse<ArticleDto> updateArticle(ArticleRequest articleRequest, UUID id);

    PageResponse<List<ArticleDto>> getAllArticlesIsPublished(Integer pageNo, Integer pageSize);
}