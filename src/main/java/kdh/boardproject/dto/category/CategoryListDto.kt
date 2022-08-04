package kdh.boardproject.dto.category

import kdh.boardproject.entity.Category
import lombok.Data
import java.time.LocalDateTime

@Data
class CategoryListDto(category: Category) {
    private val categoryIdx: Long
    private val categoryName: String
    private val description: String
    private val createdAt: LocalDateTime

    init {
        categoryIdx = category.idx
        categoryName = category.categoryName
        description = category.description
        createdAt = category.createdAt
    }
}