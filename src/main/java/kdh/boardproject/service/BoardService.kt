package kdh.boardproject.service

import kdh.boardproject.dto.board.CreateBoardDto
import kdh.boardproject.dto.board.UpdateBoardDto
import kdh.boardproject.entity.Board
import kdh.boardproject.exception.CustomException
import kdh.boardproject.exception.ErrorCode
import kdh.boardproject.exception.KCustomException
import kdh.boardproject.repository.BoardRepository
import kdh.boardproject.repository.CategoryRepository
import kdh.boardproject.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@RequiredArgsConstructor
class BoardService {
    private val boardRepository: BoardRepository? = null
    private val userRepository: UserRepository? = null
    private val categoryRepository: CategoryRepository? = null
    fun getBoardList(categoryIdx: Long?, size: Int, page: Int): Page<Board?>? {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"))
        return boardRepository!!.findByCategoryIdx(categoryIdx, pageRequest)
    }

    fun getBoardListByTitle(categoryIdx: Long?, size: Int, page: Int, title: String?): Page<Board?>? {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"))
        return boardRepository!!.findByCategoryIdxAndTitleContainingIgnoreCase(categoryIdx, title, pageRequest)
    }

    fun getBoard(idx: Long?): Optional<Board?>? {
        val board = boardRepository!!.findOneByIdx(idx)
        board!!.orElseThrow { CustomException(ErrorCode.CATEGORY_NOT_FOUND) }
        return board
    }

    @Transactional
    fun createBoard(dto: CreateBoardDto): Board {
        val category = categoryRepository!!.findOneByIdx(dto.getCategoryIdx())
        category!!.orElseThrow { CustomException(ErrorCode.CATEGORY_NOT_FOUND) }
        val user = userRepository!!.findOneByIdx(dto.getCreatedUser())
        user!!.orElseThrow { CustomException(ErrorCode.MEMBER_NOT_FOUND) }
        val board: Board = Board.builder().title(dto.getTitle()).content(dto.getContent()).user(user.get()).category(category.get()).build()
        return boardRepository!!.save(board)
    }

    @Transactional
    fun updateBoard(idx: Long?, dto: UpdateBoardDto): Board {
        val board = boardRepository!!.findOneByIdx(idx)
        board!!.orElseThrow { KCustomException(ErrorCode.BOARD_NOT_FOUND) }
        board.get().setTitle(if (dto.getTitle() != null) dto.getTitle() else board.get().getTitle())
        board.get().setContent(if (dto.getContent() != null) dto.getContent() else board.get().getContent())
        return boardRepository.save(board.get())
    }

    @Transactional
    fun deleteBoard(idx: Long): Board {
        val board = boardRepository!!.findOneByIdx(idx)
        board!!.orElseThrow { KCustomException(ErrorCode.BOARD_NOT_FOUND) }
        boardRepository.deleteById(idx)
        return board.get()
    }
}