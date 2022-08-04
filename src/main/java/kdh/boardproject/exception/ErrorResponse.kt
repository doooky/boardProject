package kdh.boardproject.exception

import lombok.Builder
import lombok.Getter
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

@Getter
@Builder
class ErrorResponse {
    private val timestamp = LocalDateTime.now()
    private val status = 0
    private val error: String? = null
    private val code: String? = null
    private val message: String? = null

    companion object {
        fun toResponseEntity(errorCode: ErrorCode): ResponseEntity<ErrorResponse> {
            return ResponseEntity
                    .status(errorCode.httpStatus)
                    .body(ErrorResponse.builder()
                            .status(errorCode.httpStatus.value())
                            .error(errorCode.httpStatus.name)
                            .code(errorCode.name)
                            .message(errorCode.detail)
                            .build()
                    )
        }
    }
}