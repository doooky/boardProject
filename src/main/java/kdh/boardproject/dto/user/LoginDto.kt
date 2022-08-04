package kdh.boardproject.dto.user

import lombok.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class LoginDto {
    private val username: @NotNull @Size(min = 3, max = 50) String? = null
    private val password: @NotNull @Size(min = 3, max = 100) String? = null
}