package kdh.boardproject.dto.category;

import kdh.boardproject.entity.Category;
import lombok.*;

@Data
public class CategoryListDto {
    private Long categoryIdx;
    private String categoryName;
    private String description;

    public CategoryListDto(Category category){
        this.categoryIdx = category.getIdx();
        this.categoryName = category.getCategoryName();
        this.description = category.getDescription();
    }
}
