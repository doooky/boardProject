package kdh.boardproject.dto.board;

import kdh.boardproject.entity.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListDto {
    private Long idx;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public BoardListDto(Board board){
        this.idx = board.getIdx();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
    }
}
