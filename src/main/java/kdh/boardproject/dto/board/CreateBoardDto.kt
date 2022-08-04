package kdh.boardproject.dto.board

import lombok.Data

@Data
class CreateBoardDto {
    private val title: String? = null
    private val content: String? = null
    private val createdUser: Long? = null
    private val categoryIdx: Long? = null
}