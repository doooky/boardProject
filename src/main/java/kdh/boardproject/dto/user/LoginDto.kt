package kdh.boardproject.dto.user

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class LoginDto(
        @NotNull @Size(min = 3, max = 50)
        var username: String,
        @NotNull @Size(min = 3, max = 100)
        var password: String)