package kdh.boardproject.controller

import kdh.boardproject.dto.boardComment.BoardCommentDto
import kdh.boardproject.dto.boardComment.CreateBoardCommentDto
import kdh.boardproject.dto.boardComment.UpdateBoardCommentDto
import kdh.boardproject.entity.BoardComment
import kdh.boardproject.service.BoardCommentService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/boardComment")
@RequiredArgsConstructor
class BoardCommentController (private val boardCommentService: BoardCommentService){

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun getBoardCommentList(
            @PathVariable id: Long,
            @RequestParam(value = "size", defaultValue = "10") size: Int,
            @RequestParam(value = "page", defaultValue = "0") page: Int
    ): ResponseEntity<List<BoardCommentDto>> {
        val boardCommentList = boardCommentService.getBoardCommentList(id, size, page)
        val result = boardCommentList!!.stream().map { o: BoardComment? -> BoardCommentDto(o!!) }.collect(Collectors.toList())
        return ResponseEntity.ok(result)
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun createBoardComment(@RequestBody dto: CreateBoardCommentDto?): ResponseEntity<BoardCommentDto> {
        val boardComment = boardCommentService.createBoardComment(dto!!)
        val result = BoardCommentDto(boardComment)
        return ResponseEntity.ok(result)
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun updateBoardComment(@PathVariable id: Long, @RequestBody dto: UpdateBoardCommentDto?): ResponseEntity<BoardCommentDto> {
        val boardComment = boardCommentService.updateBoardComment(id, dto!!)
        val result = BoardCommentDto(boardComment)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    fun deleteBoardComment(@PathVariable id: Long): ResponseEntity<BoardCommentDto> {
        val boardComment = boardCommentService.deleteBoardComment(id!!)
        val result = BoardCommentDto(boardComment)
        return ResponseEntity.ok(result)
    }
}