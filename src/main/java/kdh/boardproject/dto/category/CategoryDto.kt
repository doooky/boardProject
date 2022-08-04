package kdh.boardproject.dto.category

import kdh.boardproject.dto.user.UserRequestDto
import kdh.boardproject.entity.Category
import lombok.Data

@Data
class CategoryDto(category: Category) {
    private val categoryIdx: Long
    private val categoryName: String
    private val description: String
    private val user: UserRequestDto

    init {
        categoryIdx = category.idx
        categoryName = category.categoryName
        description = category.description
        user = UserRequestDto(category.user)
    }
}