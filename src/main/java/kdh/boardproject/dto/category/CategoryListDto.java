package kdh.boardproject.dto.category;

import kdh.boardproject.entity.Category;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class CategoryListDto {
    private Long categoryIdx;
    private String categoryName;
    private String description;

    private LocalDateTime createdAt;
    public CategoryListDto(Category category){
        this.categoryIdx = category.getIdx();
        this.categoryName = category.getCategoryName();
        this.description = category.getDescription();
        this.createdAt = category.getCreatedAt();
    }
}
