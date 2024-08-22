package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog.user.User;

//Controller -> Service -> Repository

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    public BoardResponse.DetailDTO 상세보기(int id, User sessionUser) {

        //BoardRepository의 findById()에서 찾을 수 없으면 null이 아니라 Exception을 throw 했으므로 절대 null이 뜰 수 없다.
        //조인했으므로 Board, User 정보 모두 존재 -> 필요없는 정보는 빼야한다. 상세보기에는 글제목, 내용, 글쓴이이름, 권한정보(isOwner)뿐
        //비밀번호 이런 정보는 필요 없다.

        Board board = boardRepository.findById(id);


        /*
        //더 깔끔하게 하기 위해서 아래 검증은 BoardResponse로 옮겨줬다. session과 함께 전달. 그게 코드적으로 깔끔해서.
            if (board.getUser().getId() == sessionUser.getId()) {
            isOwner = true;

        }*/


        return new BoardResponse.DetailDTO(board, sessionUser);

    }
}
