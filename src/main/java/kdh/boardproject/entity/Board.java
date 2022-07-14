package kdh.boardproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idx")
    private Long idx;

    @Column(name="title")
    private String title;

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
    @JoinColumn(name = "category_idx")
    private Category category;

    @Builder
    public Board(String title, String content, User user, Category category){
        this.title = title;
        this.content = content;
        this.user = user;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

}
