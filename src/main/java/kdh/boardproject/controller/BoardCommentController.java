package kdh.boardproject.controller;

import io.swagger.models.Response;
import kdh.boardproject.dto.boardComment.BoardCommentDto;
import kdh.boardproject.dto.boardComment.CreateBoardCommentDto;
import kdh.boardproject.dto.boardComment.UpdateBoardCommentDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.entity.BoardComment;
import kdh.boardproject.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boardComment")
@RequiredArgsConstructor
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    @GetMapping("list/{id}")
    public ResponseEntity<List<BoardCommentDto>> getBoardCommentList(
            @PathVariable Long id,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "page", defaultValue = "0") int page
    ){
        Page<BoardComment> boardCommentList = boardCommentService.getBoardCommentList(id, size, page);
        List<BoardCommentDto> result = boardCommentList.stream().
                map(o -> new BoardCommentDto(o)).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping("create")
    public ResponseEntity<BoardCommentDto> createBoardComment(@RequestBody CreateBoardCommentDto dto){
        BoardComment boardComment = boardCommentService.createBoardComment(dto);
        BoardCommentDto result = new BoardCommentDto(boardComment);
        return ResponseEntity.ok(result);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<BoardCommentDto> updateBoardComment(@PathVariable Long id, @RequestBody UpdateBoardCommentDto dto){
        BoardComment boardComment = boardCommentService.updateBoardComment(id, dto);
        BoardCommentDto result = new BoardCommentDto(boardComment);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<BoardCommentDto> deleteBoardComment(@PathVariable Long id){
        BoardComment boardComment = boardCommentService.deleteBoardComment(id);
        BoardCommentDto result = new BoardCommentDto(boardComment);
        return ResponseEntity.ok(result);
    }
}
