package kdh.boardproject.dto.category

import kdh.boardproject.entity.Category
import lombok.Data
import java.time.LocalDateTime

@Data
class CategoryListDto(category: Category) {
    var categoryIdx: Long?
    var categoryName: String
    var description: String
    var createdAt: LocalDateTime?

    init {
        categoryIdx = category.idx
        categoryName = category.categoryName
        description = category.description
        createdAt = category.createdAt
    }
}