package com.example.mini_project.service.implementation;


import com.example.mini_project.exception.NotFoundExceptionClass;
import com.example.mini_project.model.Article;
import com.example.mini_project.model.Bookmark;
import com.example.mini_project.model.User;
import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.BookmarkDto;
import com.example.mini_project.model.request.BookmarkRequest;
import com.example.mini_project.model.response.PageResponse;
import com.example.mini_project.repository.ArticleRepository;
import com.example.mini_project.repository.BookmarkRepository;
import com.example.mini_project.repository.UserRepository;
import com.example.mini_project.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private ArticleRepository articleRepository;

    @Override
    public ArticleDto saveBookmarkArticle(UUID id, BookmarkRequest bookmarkRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("This user is not found!"));
        Article article = articleRepository.findById(bookmarkRequest.getArticleId()).orElseThrow(() -> new NotFoundExceptionClass("Not found!"));
        return bookmarkRepository.save(bookmarkRequest.toEntity(article, user)).getArticle().toDto();

    }

    @Override
    public PageResponse<List<ArticleDto>> getBookmarkArticleByUserId(UUID id, Integer page, Integer size) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("This user is not found!"));
        Pageable pageable = PageRequest.of(page, size);
        Page<BookmarkDto> pageResult = bookmarkRepository.findAllUserById(pageable, user.getId()).map(Bookmark::toDto);
        List<ArticleDto> articles = new ArrayList<>();
        for (BookmarkDto bookmarkDto : pageResult) {
            articles.add(bookmarkDto.getArticle());
        }
        return PageResponse.<List<ArticleDto>>builder()
                .message("Get bookmark article successfully")
                .payload(articles)
                .status(HttpStatus.OK)
                .page(page)
                .size(size)
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }

    @Override
    public void deleteBookmark(UUID id, UUID articleId) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("This user's id is not found!"));
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NotFoundExceptionClass("This article's id is not found"));
        bookmarkRepository.deleteBookmarkByArticleIdAndUserId(user.getId(), article.getId());
    }
}



