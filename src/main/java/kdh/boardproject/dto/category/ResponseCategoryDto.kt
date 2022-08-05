package kdh.boardproject.dto.category

import lombok.Data

@Data
class ResponseCategoryDto (
     var categoryName: String ,
     var description: String ,
     var userIdx: Long
)