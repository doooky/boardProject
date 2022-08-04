package kdh.boardproject.service

import kdh.boardproject.dto.category.CategoryDto
import kdh.boardproject.dto.category.CategoryListDto
import kdh.boardproject.dto.category.DeleteCategoryDto
import kdh.boardproject.dto.category.ResponseCategoryDto
import kdh.boardproject.entity.Category
import kdh.boardproject.exception.ErrorCode
import kdh.boardproject.exception.KCustomException
import kdh.boardproject.repository.CategoryRepository
import kdh.boardproject.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@RequiredArgsConstructor
class CategoryService {
    private val categoryRepository: CategoryRepository? = null
    private val userRepository: UserRepository? = null
    val categoryList: List<CategoryListDto>
        get() {
            val categoryList = categoryRepository!!.findAll()
            return categoryList.stream().map { o: Category? -> CategoryListDto(o!!) }.collect(Collectors.toList())
        }

    @Transactional
    fun createCategory(categoryDto: ResponseCategoryDto): CategoryDto {
        checkDuplicateCategory(categoryDto.getCategoryName())
        val user = userRepository!!.findOneByIdx(categoryDto.getUserIdx())
        user!!.orElseThrow { KCustomException(ErrorCode.MEMBER_NOT_FOUND) }
        val category: Category = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .description(categoryDto.getDescription())
                .user(user.get())
                .build()
        categoryRepository!!.save(category)
        return CategoryDto(category)
    }

    @Transactional
    fun updateCategory(idx: Long, dto: ResponseCategoryDto): CategoryDto {
        val category = checkEmptyCategory(idx)
        category.setCategoryName(if (dto.getCategoryName() != null) dto.getCategoryName() else category.getCategoryName())
        category.setDescription(if (dto.getDescription() != null) dto.getDescription() else category.getDescription())
        category.updatedAt()
        categoryRepository!!.save(category)
        return CategoryDto(category)
    }

    @Transactional
    fun deleteCategory(idx: Long): DeleteCategoryDto {
        val category = CategoryDto(checkEmptyCategory(idx))
        categoryRepository!!.deleteById(idx)
        return DeleteCategoryDto("삭제 성공", category)
    }

    private fun checkEmptyCategory(idx: Long): Category {
        val category = categoryRepository!!.findOneByIdx(idx)
        category!!.orElseThrow { KCustomException(ErrorCode.CATEGORY_NOT_FOUND) }
        return category.get()
    }

    private fun checkDuplicateCategory(categoryName: String) {
        val category = categoryRepository!!.findOneByCategoryName(categoryName)
        if (!category!!.isEmpty) {
            throw KCustomException(ErrorCode.DUPLICATE_RESOURCE)
        }
    }
}