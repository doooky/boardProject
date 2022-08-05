package kdh.boardproject.repository

import kdh.boardproject.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoryRepository : JpaRepository<Category, Long> {
    override fun findAll(): List<Category>
    fun findOneByCategoryName(categoryName: String): Category?
    fun findOneByIdx(idx: Long): Category?
}