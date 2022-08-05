package kdh.boardproject.exception

import lombok.AllArgsConstructor
import lombok.Getter

class CustomException(errorCode: ErrorCode) : RuntimeException(){
     val errorCode : ErrorCode = errorCode
}
