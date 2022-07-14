package kdh.boardproject.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteCategoryDto {
    private String message;
    private CategoryDto category;
}
