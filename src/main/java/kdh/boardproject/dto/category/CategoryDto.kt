package kdh.boardproject.dto.category

import kdh.boardproject.dto.user.UserRequestDto
import kdh.boardproject.entity.Category
import lombok.Data

@Data
class CategoryDto(category: Category) {
    var categoryIdx: Long?
    var categoryName: String
    var description: String
    var user: UserRequestDto

    init {
        categoryIdx = category.idx
        categoryName = category.categoryName
        description = category.description
        user = UserRequestDto(category.user)
    }
}