package kdh.boardproject.service;

import kdh.boardproject.dto.category.CategoryDto;
import kdh.boardproject.dto.category.CategoryListDto;
import kdh.boardproject.dto.category.DeleteCategoryDto;
import kdh.boardproject.dto.category.ResponseCategoryDto;
import kdh.boardproject.entity.Category;
import kdh.boardproject.entity.User;
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
        if(user.isEmpty()){
            throw new NotFoundException("존재하지 않는 회원입니다.");
        }

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
        if(category.isEmpty()){
            throw new NotFoundException("존재하지 않는 카테고리입니다.");
        }
        return category.get();
    }

    private void checkDuplicateCategory(String categoryName){
        Optional<Category> category = categoryRepository.findOneByCategoryName(categoryName);
        if(!category.isEmpty()){
            throw new DuplicateException("이미 존재하는 카테고리 입니다.");
        }
    }
}
