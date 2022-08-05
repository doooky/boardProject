package kdh.boardproject.exception

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    val log = KotlinLogging.logger {}
    @ExceptionHandler(value = [ConstraintViolationException::class, DataIntegrityViolationException::class])
    protected fun handleDataException(): ResponseEntity<ErrorResponse> {


        log.error("handleDataException throw Exception : {}", ErrorCode.DUPLICATE_RESOURCE)
        return ErrorResponse.Companion.toResponseEntity(ErrorCode.DUPLICATE_RESOURCE)
    }

    @ExceptionHandler(value = [CustomException::class])
    protected fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        log.error("handleCustomException throw CustomException : {}", e.stackTrace)
        return ErrorResponse.Companion.toResponseEntity(e.errorCode!!)
    }
}