package kdh.boardproject.controller

import kdh.boardproject.dto.category.CategoryDto
import kdh.boardproject.dto.category.CategoryListDto
import kdh.boardproject.dto.category.DeleteCategoryDto
import kdh.boardproject.dto.category.ResponseCategoryDto
import kdh.boardproject.service.CategoryService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
class CategoryController {
    private val categoryService: CategoryService? = null

    @get:PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @get:GetMapping("/")
    val categoryList: ResponseEntity<List<CategoryListDto>>
        get() = ResponseEntity.ok(categoryService!!.categoryList)

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun createCategory(@RequestBody dto: ResponseCategoryDto?): ResponseEntity<CategoryDto> {
        return ResponseEntity.ok(categoryService!!.createCategory(dto!!))
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun updateCategory(@PathVariable("id") id: Long?, @RequestBody dto: ResponseCategoryDto?): ResponseEntity<CategoryDto> {
        return ResponseEntity.ok(categoryService!!.updateCategory(id!!, dto!!))
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun deleteCategory(@PathVariable("id") id: Long?): ResponseEntity<DeleteCategoryDto> {
        return ResponseEntity.ok(categoryService!!.deleteCategory(id!!))
    }
}