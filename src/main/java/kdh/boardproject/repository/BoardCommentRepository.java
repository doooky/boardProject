package kdh.boardproject.repository;

import kdh.boardproject.entity.BoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    Page<BoardComment> findByBoardIdx(Long boardIdx, Pageable pageable);
    Optional<BoardComment> findOneByIdx(Long idx);

}
