package kdh.boardproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idx")
    private Long idx;

    @Column(name="name")
    private String categoryName;

    @Column(name="description")
    private String description;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_user")
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Board> boardList = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        user.getCategoryList().add(this);
    }

    public void addBoard(Board board){
        boardList.add(board);
        board.setCategory(this);
    }

    @Builder
    public Category(String categoryName, String description, User user) {
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    @Builder
    public Category(Long idx, String categoryName, String description) {
        this.idx = idx;
        this.categoryName = categoryName;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }


}

