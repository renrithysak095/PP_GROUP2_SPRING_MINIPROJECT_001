package com.example.mini_project.service.serviceimp;

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
public class ArticleServiceImp implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    @Override
    public ArticleDto createArticle(ArticleRequest articleRequest) {
        User user = userRepository.findById(articleRequest.getUserId()).orElseThrow(() -> new NotFoundExceptionClass("User not found"));
        List<Category> categories = new ArrayList<>();
        for (CategoryRequest categoryRequest : articleRequest.getCategories()){
            Category category = categoryRepository.findByName(categoryRequest.getName()).orElseThrow(() -> new NotFoundExceptionClass("Category not found"));
            categories.add(category);
        }
        return articleRepository.save(articleRequest.toEntity(user, categories)).toDto();
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
    public CommentDto postComment(CommentRequest commentRequest, UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        return commentRepository.save(commentRequest.toEntity(article)).toDto();
    }

    @Override
    public List<CommentDto> getAllCommentsInArticle(UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        return commentRepository.findAllByArticleId(article.getId()).stream().map(Comment::toDto).collect(Collectors.toList());
    }

    @Override
    public ArticleDto getArticle(UUID id) {
        return articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found")).toDto();
    }

    @Override
    public void deleteArticle(UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        articleRepository.deleteById(article.getId());
    }

    @Override
    public ArticleDto updateArticle(ArticleRequest articleRequest, UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        User user = userRepository.findById(articleRequest.getUserId()).orElseThrow(() -> new NotFoundExceptionClass("User not found"));
        List<Category> categories = new ArrayList<>();
        for (CategoryRequest categoryRequest : articleRequest.getCategories()){
            Category category = categoryRepository.findByName(categoryRequest.getName()).orElseThrow(() -> new NotFoundExceptionClass("Category not found"));
            categories.add(category);
        }
        return articleRepository.save(articleRequest.toEntity(article.getId(), user, categories)).toDto();
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
