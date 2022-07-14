package kdh.boardproject.service;

import kdh.boardproject.dto.board.CreateBoardDto;
import kdh.boardproject.dto.board.UpdateBoardDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.entity.Category;
import kdh.boardproject.entity.User;
import kdh.boardproject.exception.DuplicateException;
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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        if(board.isEmpty()){
            throw new NotFoundException("존재하지 않는 카테고리입니다.");
        }
        return board;
    }

    @Transactional
    public Board createBoard(CreateBoardDto dto) {
        Optional<Category> category = categoryRepository.findOneByIdx(dto.getCategoryIdx());
        if(category.isEmpty()){
            throw new NotFoundException("존재하지 않는 카테고리입니다.");
        }

        Optional<User> user = userRepository.findOneByIdx(dto.getCreatedUser());
        if(user.isEmpty()){
            throw new NotFoundException("존재하지 않는 회원입니다.");
        }

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
        if(board.isEmpty()){
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }
        board.get().setTitle(dto.getTitle() != null ? dto.getTitle() : board.get().getTitle());
        board.get().setContent(dto.getContent() != null ? dto.getContent() : board.get().getContent());
        return boardRepository.save(board.get());
    }

    @Transactional
    public Board deleteBoard(Long idx){
        Optional<Board> board = boardRepository.findOneByIdx(idx);
        if(board.isEmpty()){
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }

        boardRepository.deleteById(idx);
        return board.get();
    }
}
