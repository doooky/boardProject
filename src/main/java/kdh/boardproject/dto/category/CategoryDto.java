package kdh.boardproject.dto.category;

import kdh.boardproject.dto.user.UserRequestDto;
import kdh.boardproject.entity.Category;
import kdh.boardproject.entity.User;
import lombok.*;

@Data
public class CategoryDto {
    private Long categoryIdx;
    private String categoryName;
    private String description;
    private UserRequestDto user;

    public CategoryDto(Category category){
        this.categoryIdx = category.getIdx();
        this.categoryName = category.getCategoryName();
        this.description = category.getDescription();
        this.user = new UserRequestDto(category.getUser());
    }
}
