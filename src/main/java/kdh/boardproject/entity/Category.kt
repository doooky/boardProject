package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="category")
class Category (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idx")
        var idx: Long? = null,

        @Column(name = "name")
        var categoryName: String,

        @Column(name = "description")
        var description: String,

        @Column(name = "created_at")
        var createdAt: LocalDateTime? = null,

        @Column(name = "updated_at")
        var updatedAt: LocalDateTime? = null,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "created_user")
        var user: User
) {




    init {
        createdAt = LocalDateTime.now()
        this.user = user
    }// init을 사용할 떄 초기화 할때 특별한 작업이 있을시,

    fun updatedAt() {
        updatedAt = LocalDateTime.now()
    }
}