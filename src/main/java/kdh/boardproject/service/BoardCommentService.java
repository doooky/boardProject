package kdh.boardproject.service;

import kdh.boardproject.dto.boardComment.BoardCommentDto;
import kdh.boardproject.dto.boardComment.CreateBoardCommentDto;
import kdh.boardproject.dto.boardComment.UpdateBoardCommentDto;
import kdh.boardproject.entity.Board;
import kdh.boardproject.entity.BoardComment;
import kdh.boardproject.entity.User;
import kdh.boardproject.exception.CustomException;
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

import static kdh.boardproject.exception.ErrorCode.*;

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
        board.orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        Optional<User> user = userRepository.findOneByIdx(dto.getUserIdx());
        user.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        BoardComment boardComment = BoardComment.builder().
                content(dto.getContent()).
                user(user.get()).
                board(board.get()).build();

        return boardCommentRepository.save(boardComment);

    }

    public BoardComment updateBoardComment(Long idx, UpdateBoardCommentDto dto) {
        Optional<BoardComment> boardComment = boardCommentRepository.findOneByIdx(idx);
        boardComment.orElseThrow(() -> new CustomException(BOARD_COMMENT_NOT_FOUND));

        boardComment.get().setContent(dto.getContent() != null ? dto.getContent() : boardComment.get().getContent());
        boardCommentRepository.save(boardComment.get());
        return boardComment.get();
    }

    public BoardComment deleteBoardComment(Long idx) {
        Optional<BoardComment> boardComment = boardCommentRepository.findOneByIdx(idx);
        boardComment.orElseThrow(() -> new CustomException(BOARD_COMMENT_NOT_FOUND));

        boardCommentRepository.deleteById(idx);
        return boardComment.get();
    }
}
