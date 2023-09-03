package com.example.mini_project.service;

import com.example.mini_project.model.dto.CategoryDto;
import com.example.mini_project.model.request.CategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryRequest categoryRequest);
    List<CategoryDto> getAllCategories(Integer pageNo,Integer pageSize);
    CategoryDto getCategoryById(UUID id);
    CategoryDto updateCategory(UUID id,CategoryRequest categoryRequest);
    void deleteCategory(UUID id);
}
