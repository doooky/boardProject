package kdh.boardproject.dto.board

import kdh.boardproject.dto.category.CategoryListDto
import kdh.boardproject.dto.user.UserRequestDto
import kdh.boardproject.entity.Board
import java.time.LocalDateTime


class BoardDto(board: Board) {
     val idx: Long?
     val title: String
     val content: String
     val createdAt: LocalDateTime?
     val user: UserRequestDto
     val category: CategoryListDto

    init {
        idx = board.idx
        title = board.title
        content = board.content
        createdAt = board.createdAt
        user = UserRequestDto(board.user)
        this.category = CategoryListDto(board.category)
    }
}