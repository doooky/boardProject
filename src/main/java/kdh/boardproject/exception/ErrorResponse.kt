package kdh.boardproject.exception

import lombok.Builder
import lombok.Getter
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

@Getter
@Builder
class ErrorResponse(var error: String?, var status: Int?, var code: String?, var message: String?) {
     var timestamp = LocalDateTime.now()
//    private var status = 0
//    private var error: String? = null
//    private var code: String? = null
//    private var message: String? = null

    companion object {
        fun toResponseEntity(errorCode: ErrorCode): ResponseEntity<ErrorResponse> {
            return ResponseEntity
                    .status(errorCode.httpStatus!!)
                    .body(
                            /* body = */ ErrorResponse(
                                    status = errorCode.httpStatus!!.value(),
                                    error = errorCode.httpStatus!!.name,
                                    code = errorCode.name,
                                    message = errorCode.detail
                            )
//                            ErrorResponse.builder()
//                            .status(errorCode.httpStatus.value())
//                            .error(errorCode.httpStatus.name)
//                            .code(errorCode.name)
//                            .message(errorCode.detail)
//                            .build()
                    )
        }
    }
}