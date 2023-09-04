package com.example.mini_project.service.implementation;

import com.example.mini_project.exception.NotFoundExceptionClass;
import com.example.mini_project.model.Article;
import com.example.mini_project.model.Category;
import com.example.mini_project.model.Comment;
import com.example.mini_project.model.User;
import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.CommentDto;
import com.example.mini_project.model.request.ArticleRequest;
import com.example.mini_project.model.request.CategoryRequest;
import com.example.mini_project.model.request.CommentRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.model.response.PageResponse;
import com.example.mini_project.repository.ArticleRepository;
import com.example.mini_project.repository.CategoryRepository;
import com.example.mini_project.repository.CommentRepository;
import com.example.mini_project.repository.UserRepository;
import com.example.mini_project.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    @Override
    public ApiResponse<ArticleDto> createArticle(ArticleRequest articleRequest) {
        User user = userRepository.findById(articleRequest.getUserId()).orElseThrow(() -> new NotFoundExceptionClass("User not found"));
        List<Category> categories = new ArrayList<>();
        for (CategoryRequest categoryRequest : articleRequest.getCategories()){
            Category category = categoryRepository.findByName(categoryRequest.getName()).orElseThrow(() -> new NotFoundExceptionClass("Category not found"));
            categories.add(category);
        }
        return ApiResponse.<ArticleDto>builder()
                .message("successfully create article")
                .payload(articleRepository.save(articleRequest.toEntity(user, categories)).toDto())
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public PageResponse<List<ArticleDto>> getAllArticles(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ArticleDto> pageResult = articleRepository.findAll(pageable).map(Article::toDto);
        return PageResponse.<List<ArticleDto>>builder()
                .message("successfully fetched article")
                .payload(pageResult.getContent())
                .status(HttpStatus.OK)
                .page(pageNo)
                .size(pageSize)
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }

    @Override
    public ApiResponse<CommentDto> postComment(CommentRequest commentRequest, UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        return  ApiResponse.<CommentDto>builder()
                .message("comment with article id: " + id)
                .payload(commentRepository.save(commentRequest.toEntity(article)).toDto())
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ApiResponse<List<CommentDto>> getAllCommentsInArticle(UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        return ApiResponse.<List<CommentDto>>builder()
                .message("success fetch comment by article id: " + id)
                .payload(commentRepository.findAllByArticleId(article.getId()).stream().map(Comment::toDto).collect(Collectors.toList()))
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<ArticleDto> getArticle(UUID id) {
        return  ApiResponse.<ArticleDto>builder()
                .message("comment with article id: " + id)
                .payload(articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found")).toDto())
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<ArticleDto> deleteArticle(UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        articleRepository.deleteById(article.getId());
        return ApiResponse.<ArticleDto>builder()
                .message("deleted successfully")
                .payload(null)
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<ArticleDto> updateArticle(ArticleRequest articleRequest, UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        User user = userRepository.findById(articleRequest.getUserId()).orElseThrow(() -> new NotFoundExceptionClass("User not found"));
        List<Category> categories = new ArrayList<>();
        for (CategoryRequest categoryRequest : articleRequest.getCategories()){
            Category category = categoryRepository.findByName(categoryRequest.getName()).orElseThrow(() -> new NotFoundExceptionClass("Category not found"));
            categories.add(category);
        }
        return  ApiResponse.<ArticleDto>builder()
                .message("successfully update article")
                .payload(articleRepository.save(articleRequest.toEntity(article.getId(), user, categories)).toDto())
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public PageResponse<List<ArticleDto>> getAllArticlesIsPublished(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ArticleDto> pageResult = articleRepository.findAllByPublished(pageable, false).map(Article::toDto);
        return PageResponse.<List<ArticleDto>>builder()
                .message("successfully fetched article")
                .payload(pageResult.getContent())
                .status(HttpStatus.OK)
                .page(pageNo)
                .size(pageSize)
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }
}
