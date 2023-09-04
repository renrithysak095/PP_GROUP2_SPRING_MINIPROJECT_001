package com.example.mini_project.service.implementation;
import com.example.mini_project.exception.NotFoundExceptionClass;
import com.example.mini_project.exception.NullExceptionClass;
import com.example.mini_project.model.Category;
import com.example.mini_project.model.dto.CategoryDto;
import com.example.mini_project.model.request.CategoryRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.model.response.PageResponse;
import com.example.mini_project.repository.CategoryRepository;
import com.example.mini_project.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public ApiResponse<CategoryDto> createCategory(CategoryRequest categoryRequest) {
        if(categoryRequest.getName().isBlank()){
            throw new NullExceptionClass("A category name field is required!","category");
        }else {
            return ApiResponse.<CategoryDto>builder()
                    .message("successfully create category.")
                    .payload(categoryRepository.save(categoryRequest.toEntity(categoryRequest.getName())).toDto())
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public PageResponse<List<CategoryDto>> getAllCategories(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<CategoryDto> page = categoryRepository.findAll(pageable).map(Category::toDto);
        return PageResponse.<List<CategoryDto>>builder()
                .message("successfully fetched category")
                .payload(page.getContent())
                .status(HttpStatus.OK)
                .page(pageNo)
                .size(pageSize)
                .totalElement(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @Override
    public ApiResponse<CategoryDto> getCategoryById(UUID id) {
        return ApiResponse.<CategoryDto>builder()
                .message("category with id: " + id)
                .payload(categoryRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("A category with ID: "+id+" not found!")).toDto())
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse<CategoryDto> updateCategory(UUID id, CategoryRequest categoryRequest) {
        if (categoryRequest.getName().isBlank()) {
            throw new NullExceptionClass("A category name field is required!","category");
        }else{
            Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundExceptionClass("A category with ID: "+id+" not exist!"));
            return ApiResponse.<CategoryDto>builder()
                    .message("successfully update category with ID: "+id)
                    .payload(categoryRepository.save(categoryRequest.toEntity(id,category.getName())).toDto())
                    .status(HttpStatus.OK)
                    .build();
        }
    }

    @Override
    public ApiResponse<CategoryDto> deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new NotFoundExceptionClass("A category with ID: "+id+" not exist!"));
        categoryRepository.deleteById(category.getId());
        return ApiResponse.<CategoryDto>builder()
                .message("deleted successfully")
                .payload(null)
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public PageResponse<List<CategoryDto>> searchCategoryByName(String name,Integer pageNo,Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<CategoryDto> page =categoryRepository.findByNameContainingIgnoreCase(pageable,name).map(Category::toDto);
        if(page.isEmpty()){
            throw new NotFoundExceptionClass("Category not found!");
        }else {
            return PageResponse.<List<CategoryDto>>builder()
                    .message("successfully fetched category")
                    .payload(page.getContent())
                    .status(HttpStatus.OK)
                    .page(pageNo)
                    .size(pageSize)
                    .totalElement(page.getTotalElements())
                    .totalPages(page.getTotalPages())
                    .build();
        }
    }

}
