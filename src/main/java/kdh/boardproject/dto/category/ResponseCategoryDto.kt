package kdh.boardproject.dto.category

import lombok.Data

@Data
class ResponseCategoryDto {
    private val categoryName: String? = null
    private val description: String? = null
    private val userIdx: Long? = null
}