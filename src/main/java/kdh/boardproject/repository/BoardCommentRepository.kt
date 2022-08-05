package kdh.boardproject.repository

import kdh.boardproject.entity.BoardComment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BoardCommentRepository : JpaRepository<BoardComment, Long> {
    fun findByBoardIdx(boardIdx: Long, pageable: Pageable): Page<BoardComment?>?
    fun findOneByIdx(idx: Long): Optional<BoardComment?>?
}