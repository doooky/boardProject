package kdh.boardproject.entity

import lombok.*
import javax.persistence.*

@Entity // DB의 테이블과 1대1 매핑되는 객체.
@Table(name = "authority")
//@AllArgsConstructor //kotlin 에서 적용시킬 수 있는 방법 찾아보기.
class Authority (
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     var idx: Long? = null,

    @Column(name = "name", length = 50)
     var authorityName: String
)