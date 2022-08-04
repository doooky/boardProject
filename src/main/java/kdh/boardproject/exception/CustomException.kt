package kdh.boardproject.exception

import lombok.AllArgsConstructor
import lombok.Getter

@Getter
@AllArgsConstructor
class CustomException : RuntimeException() {
    private val errorCode: ErrorCode? = null
}