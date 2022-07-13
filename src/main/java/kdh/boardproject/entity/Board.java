package kdh.boardproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
public class Board {

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(name="idx")
    private Long idx;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="createdAt")
    private Date createdAt;

    @Column(name="updatedAt")
    private Date updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "createdUser")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_idx")
    private Category category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardComment> boardCommentList = new ArrayList<>();

    public void setUser(User user){
        this.user = user;
        user.getBoardList().add(this);
    }

    public void setCategory(Category category){
        this.category = category;
        category.getBoardList().add(this);
    }

    public void addBoardComment(BoardComment boardComment){
        boardCommentList.add(boardComment);
        boardComment.setBoard(this);
    }
}
