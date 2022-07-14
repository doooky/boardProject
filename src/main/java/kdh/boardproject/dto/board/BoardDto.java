package kdh.boardproject.dto.board;

import kdh.boardproject.dto.category.CategoryListDto;
import kdh.boardproject.dto.user.UserRequestDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.entity.BoardComment;
import kdh.boardproject.entity.Category;
import kdh.boardproject.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDto {
    private Long idx;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private UserRequestDto user;
    private CategoryListDto category;

    public BoardDto(Board board){
        this.idx = board.getIdx();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.user = new UserRequestDto(board.getUser());
        this.category = new CategoryListDto(board.getCategory());
    }
}
