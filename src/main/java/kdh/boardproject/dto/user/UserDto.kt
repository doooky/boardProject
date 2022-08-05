package kdh.boardproject.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


class UserDto
(
        val username: @NotNull @Size(min = 3, max = 50) String = "",
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        val password: @NotNull @Size(min = 3, max = 100) String = "",
        val nickname: @NotNull @Size(min = 3, max = 50) String = ""
        )
