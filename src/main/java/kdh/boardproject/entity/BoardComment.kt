package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "board_comment")
class BoardComment (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        @Column(name = "idx")
        val idx: Long? = null,

        @Column(name = "content")
        var content: String,

        @JoinColumn(name = "created_user")
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        var user: User,

        @JoinColumn(name = "board_idx")
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        var board: Board,

        @Column(name = "createdAt")
        var createdAt: LocalDateTime? = null,

        @Column(name = "updatedAt")
        var updatedAt: LocalDateTime? = null

        ) {

    init {
        this.createdAt = LocalDateTime.now()
    }
}