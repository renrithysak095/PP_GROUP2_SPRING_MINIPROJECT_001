package com.example.mini_project.repository;

import com.example.mini_project.model.Bookmark;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, UUID> {
    @Transactional
    void deleteBookmarkByUserIdAndArticleId(UUID id, UUID articleId);

    Page<Bookmark> findAllByUserId(Pageable pageable, UUID id);
}
