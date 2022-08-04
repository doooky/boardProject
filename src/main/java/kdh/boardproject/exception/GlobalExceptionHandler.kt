package kdh.boardproject.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [ConstraintViolationException::class, DataIntegrityViolationException::class])
    protected fun handleDataException(): ResponseEntity<ErrorResponse> {
        GlobalExceptionHandler.log.error("handleDataException throw Exception : {}", ErrorCode.DUPLICATE_RESOURCE)
        return ErrorResponse.Companion.toResponseEntity(ErrorCode.DUPLICATE_RESOURCE)
    }

    @ExceptionHandler(value = [KCustomException::class])
    protected fun handleCustomException(e: KCustomException): ResponseEntity<ErrorResponse> {
        GlobalExceptionHandler.log.error("handleCustomException throw CustomException : {}", e.getErrorCode())
        return ErrorResponse.Companion.toResponseEntity(e.getErrorCode())
    }
}