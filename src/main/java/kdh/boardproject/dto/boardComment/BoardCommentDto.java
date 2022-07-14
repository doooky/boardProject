package kdh.boardproject.dto.boardComment;

import kdh.boardproject.dto.board.BoardListDto;
import kdh.boardproject.dto.user.UserDto;
import kdh.boardproject.dto.user.UserRequestDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.entity.BoardComment;
import kdh.boardproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BoardCommentDto {
    private Long idx;
    private String content;
    private UserRequestDto user;
    private BoardListDto board;

    public BoardCommentDto(BoardComment boardComment){
        this.idx = boardComment.getIdx();
        this.content = boardComment.getContent();
        this.user = new UserRequestDto(boardComment.getUser());
        this.board = new BoardListDto(boardComment.getBoard());
    }
}
