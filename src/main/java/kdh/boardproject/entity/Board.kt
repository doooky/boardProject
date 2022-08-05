package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name="board")
//field -> 밸리데이션 ㅊ체크 할때는 다해야함.
//AllArgsConstructor - 생성자 다 생성해줘야함

class Board (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)//baseEntity를 만들어서 상속받아 쓰게 하면 좋음 . 맵드 어쩌구
        var idx: Long? = null,
//    , //nullable로 지정했지만 실제로 null 이 들어가지 않는다.

        @Column(name="title")
        var title : String,

        @Column(name="content")
        var content : String,

        @Column(name="createdAt")
        var createdAt : LocalDateTime? = LocalDateTime.now(),

        @Column(name="updatedAt")
        var updatedAt : LocalDateTime? = null,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "created_user")
        var user : User,

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_idx")
        var category: Category
)
