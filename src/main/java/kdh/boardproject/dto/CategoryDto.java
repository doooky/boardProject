package kdh.boardproject.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryIdx;
    private String categoryName;
    private String description;
    private Long userIdx;
}
