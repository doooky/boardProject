package kdh.boardproject.dto.board

import kdh.boardproject.dto.category.CategoryListDto
import kdh.boardproject.dto.user.UserRequestDto
import kdh.boardproject.entity.Board
import lombok.Data
import java.time.LocalDateTime

@Data
class BoardDto(board: Board) {
    private val idx: Long
    private val title: String
    private val content: String
    private val createdAt: LocalDateTime
    private val user: UserRequestDto
    private val category: CategoryListDto

    init {
        idx = board.idx
        title = board.title
        content = board.content
        createdAt = board.createdAt
        user = UserRequestDto(board.user)
        this.category = CategoryListDto(board.category)
    }
}