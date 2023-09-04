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

    Page<Bookmark> findAllUserById(Pageable pageable, UUID id);

    @Transactional
    Void deleteBookmarkByArticleIdAndUserId(UUID id,UUID article_id);

}
