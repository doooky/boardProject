package kdh.boardproject.dto.board

import kdh.boardproject.entity.Board
import java.time.LocalDateTime


class BoardListDto(board: Board) {
     val idx: Long?
     val title: String
     val content: String
     val createdAt: LocalDateTime?

    init {
        idx = board.idx
        title = board.title
        content = board.content
        createdAt = board.createdAt
    }
}