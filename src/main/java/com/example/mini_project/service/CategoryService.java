package com.example.mini_project.service;

import com.example.mini_project.model.dto.CategoryDto;
import com.example.mini_project.model.request.CategoryRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.model.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    ApiResponse<CategoryDto> createCategory(CategoryRequest categoryRequest);
    PageResponse<List<CategoryDto>> getAllCategories(Integer pageNo, Integer pageSize);
    ApiResponse<CategoryDto> getCategoryById(UUID id);
    ApiResponse<CategoryDto> updateCategory(UUID id,CategoryRequest categoryRequest);
    ApiResponse<CategoryDto> deleteCategory(UUID id);
    PageResponse<List<CategoryDto>> searchCategoryByName(String name,Integer pageNo,Integer pageSize);
}
