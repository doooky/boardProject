package kdh.boardproject.repository;

import kdh.boardproject.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAll(Pageable pageable);

    // 대소문자 무시하고 게시물 제목 검색
    Page<Board> findByCategoryIdxAndTitleContainingIgnoreCase(Long categoryIdx, String title, Pageable pageable);
    Page<Board> findByCategoryIdx(Long categoryIdx, Pageable pageable);

    Optional<Board> findOneByIdx(Long idx);
}
