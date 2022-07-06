package kdh.boardproject.entity;
import lombok.*;

import javax.persistence.*;

@Entity // DB의 테이블과 1대1 매핑되는 객체.
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "name", length = 50)
    private String authorityName;

}
