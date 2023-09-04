package com.example.mini_project.service.serviceimp;

import com.example.mini_project.exception.NotFoundExceptionClass;
import com.example.mini_project.model.Article;
import com.example.mini_project.model.Bookmark;
import com.example.mini_project.model.User;
import com.example.mini_project.model.dto.ArticleDto;
import com.example.mini_project.model.dto.BookmarkDto;
import com.example.mini_project.model.request.BookmarkRequest;
import com.example.mini_project.model.response.ApiResponse;
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
    private final ArticleRepository articleRepository;
    @Override
    public ApiResponse<ArticleDto> bookmarkOnArticle(BookmarkRequest bookmarkRequest, UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("User not found"));
        Article article = articleRepository.findById(bookmarkRequest.getArticleId()).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        return ApiResponse.<ArticleDto>builder()
                .message("successful bookmark on article")
                .payload(bookmarkRepository.save(bookmarkRequest.toEntity(article, user)).getArticle().toDto())
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<BookmarkDto> removeBookmarkFromArticle(UUID id, UUID articleId) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("User not found"));
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NotFoundExceptionClass("Article not found"));
        bookmarkRepository.deleteBookmarkByUserIdAndArticleId(user.getId(), article.getId());
        return ApiResponse.<BookmarkDto>builder()
                .message("remove successfully")
                .payload(null)
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public PageResponse<List<ArticleDto>> getBookmarkArticle(UUID id, Integer pageNo, Integer pageSize) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundExceptionClass("User not found"));
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<BookmarkDto> pageResult = bookmarkRepository.findAllByUserId(pageable, user.getId()).map(Bookmark::toDto);
        List<ArticleDto> articles = new ArrayList<>();
        for(BookmarkDto bookmarkDto : pageResult){
            articles.add(bookmarkDto.getArticle());
        }
        return PageResponse.<List<ArticleDto>>builder()
                .message("Successfully fetched bookmarks of user: " + user.getId())
                .payload(articles)
                .status(HttpStatus.OK)
                .page(pageNo)
                .size(pageSize)
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }
}
