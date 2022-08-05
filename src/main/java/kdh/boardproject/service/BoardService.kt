package kdh.boardproject.service

import kdh.boardproject.dto.board.CreateBoardDto
import kdh.boardproject.dto.board.UpdateBoardDto
import kdh.boardproject.entity.Board
import kdh.boardproject.exception.CustomException
import kdh.boardproject.exception.ErrorCode
import kdh.boardproject.repository.BoardRepository
import kdh.boardproject.repository.CategoryRepository
import kdh.boardproject.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class BoardService(
        private val boardRepository: BoardRepository,
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository
) {

    fun getBoardList(categoryIdx: Long, size: Int, page: Int): List<Board?>? {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"))
        return boardRepository.findByCategoryIdx(categoryIdx, pageRequest)
    }

    fun getBoardListByTitle(categoryIdx: Long, size: Int, page: Int, title: String): List<Board?>? {
        val pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "idx"))
        return boardRepository.findByCategoryIdxAndTitleContainingIgnoreCase(categoryIdx, title, pageRequest)
    }

    fun getBoard(idx: Long): Board? {
        return boardRepository.findOneByIdx(idx)
                ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)
//
//
    }

    @Transactional
    fun createBoard(dto: CreateBoardDto): Board {
        val category = categoryRepository.findOneByIdx(dto.categoryIdx)
            ?: throw CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        val user = userRepository.findOneByIdx(dto.createdUser)
            ?: throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
        val board: Board =
                Board(title = dto.title,
                        content = dto.content,
                        user = user,
                        category = category
                        )
//                Board.builder().title(dto.getTitle()).content(dto.getContent()).user(user.get()).category(category.get()).build()
        return boardRepository.save(board)
    }

    @Transactional
    fun updateBoard(idx: Long, dto: UpdateBoardDto): Board {
        val board = boardRepository.findOneByIdx(idx)
            ?: throw CustomException(ErrorCode.BOARD_NOT_FOUND)
        board.title = (if (dto.title != null) dto.title else board.title)
        board.content = (if (dto.content != null) dto.content else board.content)
        return boardRepository.save(board) // 없어도됨.
    }

    @Transactional
    fun deleteBoard(idx: Long): Board {
        val board = boardRepository.findOneByIdx(idx)
            ?: throw CustomException(ErrorCode.BOARD_NOT_FOUND)
        boardRepository.deleteById(idx)
        return board // get은 optional 에서 쓰였던 것.
    }
}