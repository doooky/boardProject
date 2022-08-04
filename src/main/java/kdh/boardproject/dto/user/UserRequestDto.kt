package kdh.boardproject.dto.user

import kdh.boardproject.entity.User
import lombok.Data

@Data
class UserRequestDto(user: User) {
    private val idx: Long
    private val id: String
    private val name: String

    init {
        idx = user.idx
        id = user.id
        name = user.name
    }
}