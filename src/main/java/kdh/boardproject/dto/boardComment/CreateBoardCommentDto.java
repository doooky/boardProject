package kdh.boardproject.dto.boardComment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateBoardCommentDto {
    private String content;
    private Long userIdx;
    private Long boardIdx;
}
