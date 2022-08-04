package kdh.boardproject.repository

import kdh.boardproject.entity.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

//<사용하는 entity, primary Key의 타입>
//JpaREpository를 사용하게 되면 @Repository를 붙이지 않아도 된다.
interface BoardRepository : JpaRepository<Board?, Long?> {
    override fun findAll(pageable: Pageable): Page<Board?>

    // 대소문자 무시하고 게시물 제목 검색
    fun findByCategoryIdxAndTitleContainingIgnoreCase(categoryIdx: Long?, title: String?, pageable: Pageable?): Page<Board?>?
    fun findByCategoryIdx(categoryIdx: Long?, pageable: Pageable?): Page<Board?>?
    fun findOneByIdx(idx: Long?): Optional<Board?>?
}