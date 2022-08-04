package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Category @Builder constructor(@field:Column(name = "name") private val categoryName: String, @field:Column(name = "description") private val description: String, user: User) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private val idx: Long? = null

    @Column(name = "created_at")
    private val createdAt: LocalDateTime

    @Column(name = "updated_at")
    private var updatedAt: LocalDateTime? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user")
    private val user: User

    init {
        createdAt = LocalDateTime.now()
        this.user = user
    }

    fun updatedAt() {
        updatedAt = LocalDateTime.now()
    }
}