package kdh.boardproject.controller;

import kdh.boardproject.dto.category.CategoryDto;
import kdh.boardproject.dto.category.CategoryListDto;
import kdh.boardproject.dto.category.DeleteCategoryDto;
import kdh.boardproject.dto.category.ResponseCategoryDto;
import kdh.boardproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("list")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<CategoryListDto>> getCategoryList(){
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody ResponseCategoryDto dto){
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id, @RequestBody ResponseCategoryDto dto){
        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<DeleteCategoryDto> deleteCategory(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
