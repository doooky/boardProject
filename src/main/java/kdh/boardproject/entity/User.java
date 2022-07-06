package kdh.boardproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity // DB의 테이블과 1대1 매핑되는 객체.
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonIgnore
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name="id", length = 50, unique = true)
    private String id;

    @JsonIgnore
    @Column(name="pw", length = 100)
    private String pw;

    @Column(name="name", length = 50)
    private String name;

    @JsonIgnore
    @Column(name="activated")
    private boolean activated;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_idx", referencedColumnName = "idx")},
            inverseJoinColumns = {@JoinColumn(name = "authority_idx", referencedColumnName = "idx")})
    private Set<Authority> authorities;

}
