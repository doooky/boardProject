package kdh.boardproject.dto.category

import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
class DeleteCategoryDto {
    private val message: String? = null
    private val category: CategoryDto? = null
}