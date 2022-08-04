package kdh.boardproject.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class UserDto {
    private val username: @NotNull @Size(min = 3, max = 50) String? = null

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private val password: @NotNull @Size(min = 3, max = 100) String? = null
    private val nickname: @NotNull @Size(min = 3, max = 50) String? = null
}