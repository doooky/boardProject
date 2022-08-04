package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
class Board @Builder constructor(@field:Column(name = "title") private val title: String, @field:Column(name = "content") private val content: String, @field:JoinColumn(name = "created_user") @field:ManyToOne(fetch = FetchType.LAZY) @field:JsonIgnore private val user: User, @field:JoinColumn(name = "category_idx") @field:ManyToOne(fetch = FetchType.LAZY) @field:JsonIgnore private val category: Category) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private val idx: Long? = null

    @Column(name = "createdAt")
    private val createdAt: LocalDateTime

    @Column(name = "updatedAt")
    private val updatedAt: LocalDateTime? = null

    init {
        createdAt = LocalDateTime.now()
    }
}