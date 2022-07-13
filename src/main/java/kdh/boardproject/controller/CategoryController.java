package kdh.boardproject.controller;

import io.swagger.annotations.Authorization;
import kdh.boardproject.dto.CategoryDto;
import kdh.boardproject.entity.Category;
import kdh.boardproject.service.CategoryService;
import lombok.Data;
import lombok.Getter;
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
    public ResponseEntity<List<Category>> getCategoryList(){
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto dto){
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto dto){
        dto.setCategoryIdx(id);
        return ResponseEntity.ok(categoryService.updateCategory(dto));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void updateCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
    }
}
