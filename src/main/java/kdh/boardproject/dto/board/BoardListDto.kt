package kdh.boardproject.dto.board

import kdh.boardproject.entity.Board
import lombok.Data
import java.time.LocalDateTime

@Data
class BoardListDto(board: Board) {
    private val idx: Long
    private val title: String
    private val content: String
    private val createdAt: LocalDateTime

    init {
        idx = board.idx
        title = board.title
        content = board.content
        createdAt = board.createdAt
    }
}