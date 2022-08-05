package kdh.boardproject.controller

import kdh.boardproject.dto.board.BoardDto
import kdh.boardproject.dto.board.BoardListDto
import kdh.boardproject.dto.board.CreateBoardDto
import kdh.boardproject.dto.board.UpdateBoardDto
import kdh.boardproject.service.BoardService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
class BoardController (private val boardService: BoardService){

    @GetMapping("/category/{categoryIdx}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun getBoardList(
            @PathVariable(value = "categoryIdx") categoryIdx: Long,
            @RequestParam(value = "size", defaultValue = "10") size: Int,
            @RequestParam(value = "page", defaultValue = "0") page: Int): List<BoardListDto?>? {
        val boardList = boardService.getBoardList(categoryIdx, size, page)
        //     Entity 를 dto로 변환하는 과정?

        return boardList?.map { it -> BoardListDto(it!!) }
    }

    @GetMapping("{categoryIdx}/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun getBoardListByTitle(
            @PathVariable(value = "categoryIdx") categoryIdx: Long,
            @RequestParam(value = "size", defaultValue = "10") size: Int,
            @RequestParam(value = "page", defaultValue = "0") page: Int,
            @RequestParam title: String): List<BoardListDto?>? {
        val boardList = boardService.getBoardListByTitle(categoryIdx, size, page, title)
//        var result = listOf<BoardListDto>()
        var result = boardList?.map{
            it -> BoardListDto(it!!)
        }
        return result

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun getBoard(@PathVariable(value = "id") id: Long): ResponseEntity<BoardDto> {
        val result = BoardDto(boardService.getBoard(id)!!)//.get()
        return ResponseEntity.ok(result)
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun createBoard(@RequestBody dto: CreateBoardDto?): ResponseEntity<BoardDto> {
        val result = BoardDto(boardService.createBoard(dto!!))
        return ResponseEntity.ok(result)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun updateBoard(@PathVariable(value = "id") id: Long, @RequestBody dto: UpdateBoardDto?): ResponseEntity<BoardDto> {
        val result = BoardDto(boardService.updateBoard(id, dto!!))
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun deleteBoard(@PathVariable id: Long?): ResponseEntity<BoardDto> {
        val result = BoardDto(boardService.deleteBoard(id!!))
        return ResponseEntity.ok(result)
    }
}