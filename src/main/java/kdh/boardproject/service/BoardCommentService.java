package kdh.boardproject.service;

import kdh.boardproject.dto.boardComment.BoardCommentDto;
import kdh.boardproject.dto.boardComment.CreateBoardCommentDto;
import kdh.boardproject.dto.boardComment.UpdateBoardCommentDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.entity.BoardComment;
import kdh.boardproject.entity.User;
import kdh.boardproject.exception.NotFoundException;
import kdh.boardproject.repository.BoardCommentRepository;
import kdh.boardproject.repository.BoardRepository;
import kdh.boardproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public Page<BoardComment> getBoardCommentList(Long boardIdx, int size, int page){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"idx"));
        return boardCommentRepository.findByBoardIdx(boardIdx, pageRequest);
    }


    public BoardComment createBoardComment(CreateBoardCommentDto dto) {
        Optional<Board> board = boardRepository.findOneByIdx(dto.getBoardIdx());
        if(board.isEmpty()){
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }

        Optional<User> user = userRepository.findOneByIdx(dto.getUserIdx());
        if(user.isEmpty()){
            throw new NotFoundException("존재하지 않는 회원입니다.");
        }

        BoardComment boardComment = BoardComment.builder().
                content(dto.getContent()).
                user(user.get()).
                board(board.get()).build();

        return boardCommentRepository.save(boardComment);

    }

    public BoardComment updateBoardComment(Long idx, UpdateBoardCommentDto dto) {
        Optional<BoardComment> boardComment = boardCommentRepository.findOneByIdx(idx);
        if(boardComment.isEmpty()){
            throw new NotFoundException("존재하지 않는 댓글입니다.");
        }

        boardComment.get().setContent(dto.getContent() != null ? dto.getContent() : boardComment.get().getContent());
        boardCommentRepository.save(boardComment.get());
        return boardComment.get();
    }

    public BoardComment deleteBoardComment(Long idx) {
        Optional<BoardComment> boardComment = boardCommentRepository.findOneByIdx(idx);
        if(boardComment.isEmpty()){
            throw new NotFoundException("존재하지 않는 댓글입니다.");
        }

        boardCommentRepository.deleteById(idx);
        return boardComment.get();
    }
}
