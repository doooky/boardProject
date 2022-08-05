package kdh.boardproject.service

import kdh.boardproject.dto.category.CategoryDto
import kdh.boardproject.dto.category.CategoryListDto
import kdh.boardproject.dto.category.DeleteCategoryDto
import kdh.boardproject.dto.category.ResponseCategoryDto
import kdh.boardproject.entity.Category
import kdh.boardproject.exception.CustomException
import kdh.boardproject.exception.ErrorCode
import kdh.boardproject.repository.CategoryRepository
import kdh.boardproject.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@RequiredArgsConstructor
class CategoryService (
        private val categoryRepository: CategoryRepository,
        private val userRepository: UserRepository
        )
{

    val categoryList: List<CategoryListDto>
        get() {
            val categoryList = categoryRepository.findAll()
            return categoryList.stream().map { o: Category? -> CategoryListDto(o!!) }.collect(Collectors.toList())
        }

    @Transactional
    fun createCategory(categoryDto: ResponseCategoryDto): CategoryDto {
        checkDuplicateCategory(categoryDto.categoryName)
        val user = userRepository.findOneByIdx(categoryDto.userIdx)
                ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
        val category: Category =
                Category(
                        categoryName = categoryDto.categoryName,
                        description = categoryDto.description,
                        user = user
                        )
//                Category.builder()
//                .categoryName(categoryDto.getCategoryName())
//                .description(categoryDto.getDescription())
//                .user(user.get())
//                .build()
        categoryRepository.save(category)
        return CategoryDto(category)
    }

    @Transactional
    fun updateCategory(idx: Long, dto: ResponseCategoryDto): CategoryDto {
        val category = checkEmptyCategory(idx)
        category.categoryName = (if (dto.categoryName != null) dto.categoryName else category.categoryName)
        category.description = (if (dto.description != null) dto.description else category.description)
        category.updatedAt()
        categoryRepository.save(category)
        return CategoryDto(category)
    }

    @Transactional
    fun deleteCategory(idx: Long): DeleteCategoryDto {
        val category = CategoryDto(checkEmptyCategory(idx))
        categoryRepository.deleteById(idx)
        return DeleteCategoryDto("삭제 성공", category)
    }

    private fun checkEmptyCategory(idx: Long): Category {
        return categoryRepository.findOneByIdx(idx)
                ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)
    }

    private fun checkDuplicateCategory(categoryName: String) {
        val category = categoryRepository.findOneByCategoryName(categoryName)


            if (category != null) {
                throw CustomException(ErrorCode.DUPLICATE_RESOURCE)
            }

    }
}