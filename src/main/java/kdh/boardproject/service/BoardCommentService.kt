package kdh.boardproject.service

import kdh.boardproject.dto.boardComment.CreateBoardCommentDto
import kdh.boardproject.dto.boardComment.UpdateBoardCommentDto
import kdh.boardproject.entity.Board
import kdh.boardproject.entity.BoardComment
import kdh.boardproject.exception.CustomException
import kdh.boardproject.exception.ErrorCode
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
class BoardCommentService (
        private var boardCommentRepository: BoardCommentRepository,
        private var boardRepository: BoardRepository,
        private var userRepository: UserRepository
        ){


    fun getBoardCommentList(boardIdx: Long, size: Int, page: Int): Page<BoardComment?>? {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"))
        return boardCommentRepository.findByBoardIdx(boardIdx, pageRequest)
    }

    fun createBoardComment(dto: CreateBoardCommentDto): BoardComment {
        val board = boardRepository.findOneByIdx(dto.boardIdx)
         ?: throw CustomException(ErrorCode.BOARD_NOT_FOUND)

        val user = userRepository.findOneByIdx(dto.userIdx)
         ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)

        val boardComment: BoardComment =
                BoardComment(content = dto.content,
                user = user,
                board = board
                )
        return boardCommentRepository.save(boardComment)
    }

    fun updateBoardComment(idx: Long, dto: UpdateBoardCommentDto): BoardComment {
        val boardComment = boardCommentRepository.findOneByIdx(idx)
                ?: throw CustomException(ErrorCode.BOARD_COMMENT_NOT_FOUND)
        boardComment.get().content = if (dto.content != null) dto.content else boardComment.get().content
//        boardCommentRepository.save(boardComment.get())
        return boardComment.get()
    }

    fun deleteBoardComment(idx: Long): BoardComment {
        val boardComment = boardCommentRepository.findOneByIdx(idx)
                ?: throw  CustomException(ErrorCode.BOARD_COMMENT_NOT_FOUND)
        boardCommentRepository.deleteById(idx)
        return boardComment.get()
    }
}

