package kdh.boardproject.dto.boardComment

import kdh.boardproject.dto.board.BoardListDto
import kdh.boardproject.dto.user.UserRequestDto
import kdh.boardproject.entity.BoardComment
import lombok.Data

@Data
class BoardCommentDto(boardComment: BoardComment) {
     val idx: Long?
     val content: String
     val user: UserRequestDto
     val board: BoardListDto

    init {
        idx = boardComment.idx
        content = boardComment.content
        user = UserRequestDto(boardComment.user)
        board = BoardListDto(boardComment.board)
    }
}