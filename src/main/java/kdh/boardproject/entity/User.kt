package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import javax.persistence.*

@Entity // DB의 테이블과 1대1 매핑되는 객체.
//@AllArgsConstructor // 모든 파라미터를 넘기는 생성자
class User (
        @JsonIgnore
        @Id
        @Column(name = "idx")
        @GeneratedValue(strategy = GenerationType.IDENTITY) var idx: Long? = null,

        @Column(name = "id", length = 50, unique = true)
         var id: String,

        @JsonIgnore
        @Column(name = "pw", length = 100)
         var pw: String,

        @Column(name = "name", length = 50)
         var name: String,

        @JsonIgnore
        @Column(name = "activated")
         var activated : Boolean,

        @ManyToMany(cascade = [CascadeType.ALL])
        @JoinTable(name = "user_authority",
                joinColumns = [JoinColumn(name = "user_idx", referencedColumnName = "idx")],
                inverseJoinColumns = [JoinColumn(name = "authority_idx", referencedColumnName = "idx")])
         var authorities: Set<Authority>? = null //    @OneToMany(mappedBy = "user")
        ) {


}