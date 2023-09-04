package com.example.mini_project.controller;
import com.example.mini_project.model.dto.CategoryDto;
import com.example.mini_project.model.request.CategoryRequest;
import com.example.mini_project.model.response.ApiResponse;
import com.example.mini_project.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
@Tag(name = "Categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok().body(categoryService.createCategory(categoryRequest));
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCategories(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(categoryService.getAllCategories(pageNo, pageSize));
    }

    @GetMapping("search")
    public ResponseEntity<?> searchCategoryByName(String name,@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok().body(categoryService.searchCategoryByName(name,pageNo,pageSize));
    }

    @GetMapping("{id}")
    public  ResponseEntity<?> getArticle(@PathVariable UUID id){
        return ResponseEntity.ok().body(categoryService.getCategoryById(id));
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<?> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder()
                .message("deleted successfully")
                .payload(null)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable UUID id){
        return ResponseEntity.ok().body(categoryService.updateCategory(id,categoryRequest));
    }
}
