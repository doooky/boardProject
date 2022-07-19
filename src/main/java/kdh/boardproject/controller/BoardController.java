package kdh.boardproject.controller;

import kdh.boardproject.dto.board.BoardDto;
import kdh.boardproject.dto.board.BoardListDto;
import kdh.boardproject.dto.board.CreateBoardDto;
import kdh.boardproject.dto.board.UpdateBoardDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("{categoryIdx}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Page<Board> getBoardList(
            @PathVariable(value = "categoryIdx") Long categoryIdx,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "page", defaultValue = "0") int page){
        Page<Board> boardList = boardService.getBoardList(categoryIdx, size, page);
        List<BoardListDto> result = boardList.stream()
                .map(o -> new BoardListDto(o))
                .collect(Collectors.toList());
        return boardList;
    }

    @GetMapping("{categoryIdx}/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<BoardListDto> getBoardListByTitle(
            @PathVariable(value = "categoryIdx") Long categoryIdx,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam String title){
        Page<Board> boardList = boardService.getBoardListByTitle(categoryIdx, size, page, title);
        List<BoardListDto> result = boardList.stream()
                .map(o -> new BoardListDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BoardDto> getBoard(@PathVariable(value = "id") Long id){
        BoardDto result = new BoardDto(boardService.getBoard(id).get());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BoardDto> createBoard(@RequestBody CreateBoardDto dto){
        BoardDto result = new BoardDto(boardService.createBoard(dto));
        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable(value = "id")  Long id, @RequestBody UpdateBoardDto dto){
        BoardDto result = new BoardDto(boardService.updateBoard(id, dto));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BoardDto> deleteBoard(@PathVariable Long id){
        BoardDto result = new BoardDto(boardService.deleteBoard(id));
        return ResponseEntity.ok(result);
    }
}
