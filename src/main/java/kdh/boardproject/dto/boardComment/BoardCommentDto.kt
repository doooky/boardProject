package kdh.boardproject.dto.boardComment

import kdh.boardproject.dto.board.BoardListDto
import kdh.boardproject.dto.user.UserRequestDto
import kdh.boardproject.entity.BoardComment
import lombok.Data

@Data
class BoardCommentDto(boardComment: BoardComment) {
    private val idx: Long
    private val content: String
    private val user: UserRequestDto
    private val board: BoardListDto

    init {
        idx = boardComment.idx
        content = boardComment.content
        user = UserRequestDto(boardComment.user)
        board = BoardListDto(boardComment.board)
    }
}