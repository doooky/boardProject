package kdh.boardproject.service;

import kdh.boardproject.dto.CategoryDto;
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

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<Category> getCategoryList(){
        return categoryRepository.findAll();
    }


    @Transactional
    public Category createCategory(CategoryDto categoryDto){
        if(categoryRepository.findOneByCategoryName(categoryDto.getCategoryName()).orElse(null) != null){
            throw new DuplicateException("이미 존재하는 카테고리 입니다.");
        }

        User user = userRepository.findOneByIdx(categoryDto.getUserIdx());

        Category category = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .description(categoryDto.getDescription())
                .user(user)
                .build();

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(CategoryDto categoryDto){
        Category category = checkEmptyCategory(categoryDto.getCategoryIdx());

        category.setCategoryName(categoryDto.getCategoryName() != null ? categoryDto.getCategoryName() : category.getCategoryName());
        category.setDescription(categoryDto.getDescription() != null ? categoryDto.getDescription() : category.getDescription());

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long idx){
        categoryRepository.deleteById(idx);
    }

    private Category checkEmptyCategory(Long idx){
        Optional<Category> category = categoryRepository.findOneByIdx(idx);
        if(category.isEmpty()){
            throw new NotFoundException("존재하지 않는 카테고리입니다.");
        }
        return category.get();
    }
}
