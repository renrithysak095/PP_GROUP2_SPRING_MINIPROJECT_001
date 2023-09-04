package com.example.mini_project.service.implementation;

import com.example.mini_project.exception.NotFoundExceptionClass;
import com.example.mini_project.model.Category;
import com.example.mini_project.model.dto.CategoryDto;
import com.example.mini_project.model.request.CategoryRequest;
import com.example.mini_project.repository.CategoryRepository;
import com.example.mini_project.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        return categoryRepository.save(categoryRequest.toEntity(categoryRequest.getName())).toDto();
    }

    @Override
    public List<CategoryDto> getAllCategories(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<CategoryDto> page = categoryRepository.findAll(pageable).map(Category::toDto);
        return page.getContent();
    }

    @Override
    public CategoryDto getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Category not found!")).toDto();
    }

    @Override
    public CategoryDto updateCategory(UUID id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("Category not found!"));
        return categoryRepository.save(categoryRequest.toEntity(id,category.getName())).toDto();
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new NotFoundExceptionClass("Category not found!"));
        categoryRepository.deleteById(id);
    }

}
