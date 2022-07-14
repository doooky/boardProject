package kdh.boardproject.dto.board;

import kdh.boardproject.entity.Board;
import lombok.Data;

@Data
public class CreateBoardDto {
    private String title;
    private String content;
    private Long createdUser;
    private Long categoryIdx;
}
