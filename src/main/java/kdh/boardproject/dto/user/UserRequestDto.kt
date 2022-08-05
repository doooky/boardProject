package kdh.boardproject.dto.user

import kdh.boardproject.entity.User
import lombok.Data

@Data
class UserRequestDto(user: User) {
     var idx: Long
     var id: String
     var name: String

    init {
        idx = user.idx!!
        id = user.id
        name = user.name
    }
}