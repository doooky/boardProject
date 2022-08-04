package kdh.boardproject.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.*
import javax.persistence.*

@Entity // DB의 테이블과 1대1 매핑되는 객체.
@Getter
@Setter
@Builder
@AllArgsConstructor // 모든 파라미터를 넘기는 생성자
@NoArgsConstructor
class User {
    @JsonIgnore
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val idx: Long? = null

    @Column(name = "id", length = 50, unique = true)
    private val id: String? = null

    @JsonIgnore
    @Column(name = "pw", length = 100)
    private val pw: String? = null

    @Column(name = "name", length = 50)
    private val name: String? = null

    @JsonIgnore
    @Column(name = "activated")
    private val activated = false

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "user_authority", joinColumns = [JoinColumn(name = "user_idx", referencedColumnName = "idx")], inverseJoinColumns = [JoinColumn(name = "authority_idx", referencedColumnName = "idx")])
    private val authorities: Set<Authority>? = null //    @OneToMany(mappedBy = "user")
    //    private List<Category> categoryList = new ArrayList<>();
    //    @OneToMany(mappedBy = "user")
    //    private List<Board> boardList = new ArrayList<>();
    //
    //    @OneToMany(mappedBy = "user")
    //    private List<BoardComment> boardCommentList = new ArrayList<>();
    //
    //    public void addCategory(Category category){
    //        categoryList.add(category);
    //        category.setUser(this);
    //    }
}