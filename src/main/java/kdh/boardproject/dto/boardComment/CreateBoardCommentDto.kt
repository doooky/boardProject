package kdh.boardproject.dto.boardComment


class CreateBoardCommentDto {
    lateinit var content: String
    var userIdx: Long = 0
    // lateinit 은 primitive  Type (Int, Float, Double, Long 등) 은 사용 불가능 하는데 어떻게 처리하는것이 제일 좋을까?
    var boardIdx: Long = 0
}