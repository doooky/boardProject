package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*

@Getter
@Setter
@Entity
@Table(name = "board_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class BoardComment @Builder constructor(@field:Column(name = "content") private val content: String, @field:JoinColumn(name = "created_user") @field:ManyToOne(fetch = FetchType.LAZY) @field:JsonIgnore private val user: User, @field:JoinColumn(name = "board_idx") @field:ManyToOne(fetch = FetchType.LAZY) @field:JsonIgnore private val board: Board) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
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