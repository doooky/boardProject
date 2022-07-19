package kdh.boardproject.service;

import kdh.boardproject.dto.board.CreateBoardDto;
import kdh.boardproject.dto.board.UpdateBoardDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.entity.Category;
import kdh.boardproject.entity.User;
import kdh.boardproject.exception.CustomException;
import kdh.boardproject.exception.NotFoundException;
import kdh.boardproject.repository.BoardRepository;
import kdh.boardproject.repository.CategoryRepository;
import kdh.boardproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static kdh.boardproject.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public Page<Board> getBoardList(Long categoryIdx, int size, int page){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"idx"));
        return boardRepository.findByCategoryIdx(categoryIdx, pageRequest);
    }

    public Page<Board> getBoardListByTitle(Long categoryIdx, int size, int page, String title){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"idx"));
        return boardRepository.findByCategoryIdxAndTitleContainingIgnoreCase(categoryIdx, title,pageRequest);
    }

    public Optional<Board> getBoard(Long idx){
        Optional<Board> board = boardRepository.findOneByIdx(idx);
        board.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));
        return board;
    }

    @Transactional
    public Board createBoard(CreateBoardDto dto) {
        Optional<Category> category = categoryRepository.findOneByIdx(dto.getCategoryIdx());
        category.orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));


        Optional<User> user = userRepository.findOneByIdx(dto.getCreatedUser());
        user.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));


        Board board = Board.builder().
                title(dto.getTitle()).
                content(dto.getContent()).
                user(user.get()).
                category(category.get()).
                build();
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Long idx, UpdateBoardDto dto) {
        Optional<Board> board = boardRepository.findOneByIdx(idx);
        board.orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        board.get().setTitle(dto.getTitle() != null ? dto.getTitle() : board.get().getTitle());
        board.get().setContent(dto.getContent() != null ? dto.getContent() : board.get().getContent());
        return boardRepository.save(board.get());
    }

    @Transactional
    public Board deleteBoard(Long idx){
        Optional<Board> board = boardRepository.findOneByIdx(idx);
        board.orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));


        boardRepository.deleteById(idx);
        return board.get();
    }
}
