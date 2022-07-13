package kdh.boardproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name="board_comment")
public class BoardComment {

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(name="idx")
    private Long idx;

    @Column(name="content")
    private String content;

    @Column(name="upper_comment_idx")
    private Long upper_comment_idx;

    @Column(name="createdAt")
    private Date createdAt;

    @Column(name="updatedAt")
    private Date updatedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "createdUser")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    public void setUser(User user){
        this.user = user;
        user.getBoardCommentList().add(this);
    }

    public void setBoard(Board board){
        this.board = board;
        board.getBoardCommentList().add(this);
    }
}
