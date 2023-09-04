package com.example.mini_project.repository;

import com.example.mini_project.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileStorage, UUID> {
}
