package kdh.boardproject.service

import kdh.boardproject.dto.boardComment.CreateBoardCommentDto
import kdh.boardproject.dto.boardComment.UpdateBoardCommentDto
import kdh.boardproject.entity.BoardComment
import kdh.boardproject.exception.ErrorCode
import kdh.boardproject.exception.KCustomException
import kdh.boardproject.repository.BoardCommentRepository
import kdh.boardproject.repository.BoardRepository
import kdh.boardproject.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class BoardCommentService {
    private val boardCommentRepository: BoardCommentRepository? = null
    private val boardRepository: BoardRepository? = null
    private val userRepository: UserRepository? = null
    fun getBoardCommentList(boardIdx: Long?, size: Int, page: Int): Page<BoardComment?>? {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"))
        return boardCommentRepository!!.findByBoardIdx(boardIdx, pageRequest)
    }

    fun createBoardComment(dto: CreateBoardCommentDto): BoardComment {
        val board = boardRepository!!.findOneByIdx(dto.getBoardIdx())
        board!!.orElseThrow { KCustomException(ErrorCode.BOARD_NOT_FOUND) }
        val user = userRepository!!.findOneByIdx(dto.getUserIdx())
        user!!.orElseThrow { KCustomException(ErrorCode.MEMBER_NOT_FOUND) }
        val boardComment: BoardComment = BoardComment.builder().content(dto.getContent()).user(user.get()).board(board.get()).build()
        return boardCommentRepository!!.save(boardComment)
    }

    fun updateBoardComment(idx: Long?, dto: UpdateBoardCommentDto): BoardComment {
        val boardComment = boardCommentRepository!!.findOneByIdx(idx)
        boardComment!!.orElseThrow { KCustomException(ErrorCode.BOARD_COMMENT_NOT_FOUND) }
        boardComment.get().setContent(if (dto.getContent() != null) dto.getContent() else boardComment.get().getContent())
        boardCommentRepository.save(boardComment.get())
        return boardComment.get()
    }

    fun deleteBoardComment(idx: Long): BoardComment {
        val boardComment = boardCommentRepository!!.findOneByIdx(idx)
        boardComment!!.orElseThrow { KCustomException(ErrorCode.BOARD_COMMENT_NOT_FOUND) }
        boardCommentRepository.deleteById(idx)
        return boardComment.get()
    }
}