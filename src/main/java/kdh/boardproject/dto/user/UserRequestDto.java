package kdh.boardproject.dto.user;

import kdh.boardproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserRequestDto {
    private Long idx;
    private String id;
    private String name;

    public UserRequestDto(User user){
        this.idx = user.getIdx();
        this.id = user.getId();
        this.name = user.getName();
    }

}
