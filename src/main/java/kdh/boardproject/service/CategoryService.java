package kdh.boardproject.service;

import kdh.boardproject.dto.category.CategoryDto;
import kdh.boardproject.dto.category.CategoryListDto;
import kdh.boardproject.dto.category.DeleteCategoryDto;
import kdh.boardproject.dto.category.ResponseCategoryDto;
import kdh.boardproject.entity.Category;
import kdh.boardproject.entity.User;
import kdh.boardproject.exception.CustomException;
import kdh.boardproject.exception.DuplicateException;
import kdh.boardproject.exception.NotFoundException;
import kdh.boardproject.repository.CategoryRepository;
import kdh.boardproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kdh.boardproject.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<CategoryListDto> getCategoryList(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryListDto> result = categoryList.stream().
                map(o -> new CategoryListDto(o)).
                collect(Collectors.toList());

        return result;
    }


    @Transactional
    public CategoryDto createCategory(ResponseCategoryDto categoryDto){
        checkDuplicateCategory(categoryDto.getCategoryName());

        Optional<User> user = userRepository.findOneByIdx(categoryDto.getUserIdx());
        user.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Category category = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .description(categoryDto.getDescription())
                .user(user.get())
                .build();

        categoryRepository.save(category);

        return new CategoryDto(category);
    }

    @Transactional
    public CategoryDto updateCategory(Long idx, ResponseCategoryDto dto){
        Category category = checkEmptyCategory(idx);

        category.setCategoryName(dto.getCategoryName() != null ? dto.getCategoryName() : category.getCategoryName());
        category.setDescription(dto.getDescription() != null ? dto.getDescription() : category.getDescription());
        category.updatedAt();

        categoryRepository.save(category);

        return new CategoryDto(category);
    }

    @Transactional
    public DeleteCategoryDto deleteCategory(Long idx){
        CategoryDto category = new CategoryDto(checkEmptyCategory(idx));

        categoryRepository.deleteById(idx);

        return new DeleteCategoryDto("삭제 성공", category);
    }

    private Category checkEmptyCategory(Long idx){
        Optional<Category> category = categoryRepository.findOneByIdx(idx);
        category.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));

        return category.get();
    }

    private void checkDuplicateCategory(String categoryName){
        Optional<Category> category = categoryRepository.findOneByCategoryName(categoryName);
        if(!category.isEmpty()){
            throw new CustomException(DUPLICATE_RESOURCE);
        }
    }
}
