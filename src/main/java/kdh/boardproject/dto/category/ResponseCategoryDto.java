package kdh.boardproject.dto.category;

import lombok.Data;

@Data
public class ResponseCategoryDto {
    private String categoryName;
    private String description;
    private Long userIdx;
}
