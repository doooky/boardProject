package kdh.boardproject.repository;

import kdh.boardproject.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Optional<Category> findOneByCategoryName(String categoryName);

    Optional<Category> findOneByIdx(Long idx);
}
