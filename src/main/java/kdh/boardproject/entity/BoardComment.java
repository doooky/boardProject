package kdh.boardproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kdh.boardproject.dto.boardComment.CreateBoardCommentDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table(name="board_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name="idx")
    private Long idx;

    @Column(name="content")
    private String content;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_user")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_idx")
    private Board board;

    @Builder
    public BoardComment(String content, User user, Board board){
        this.content = content;
        this.user = user;
        this.board = board;
        this.createdAt = LocalDateTime.now();
    }

}
