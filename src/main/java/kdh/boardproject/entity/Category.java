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

    @Builder
    public Category(String categoryName, String description, User user) {
        this.categoryName = categoryName;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public void updatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

}

