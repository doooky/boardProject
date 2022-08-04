package kdh.boardproject.dto.boardComment

import lombok.Data

@Data
class CreateBoardCommentDto {
    private val content: String? = null
    private val userIdx: Long? = null
    private val boardIdx: Long? = null
}