package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog.user.User;

//Controller -> Service -> Repository

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    public BoardResponse.DetailDTOV2 상세보기(int id, User sessionUser) {

        //BoardRepository의 findById()에서 찾을 수 없으면 null이 아니라 Exception을 throw 했으므로 null 검증 안 함
        //조인했으므로 Board, User 정보 모두 존재 -> 필요없는 정보는 빼야한다. 상세보기에는 글제목, 내용, 글쓴이이름, 권한정보(isOwner)뿐
        //비밀번호 이런 정보는 필요 없다.

        Board board = boardRepository.findById(id);


        /*
        //더 깔끔하게 하기 위해서 아래 검증은 BoardResponse로 옮겨줬다. session과 함께 전달. 그게 코드적으로 깔끔해서.
            if (board.getUser().getId() == sessionUser.getId()) {
            isOwner = true;

        }*/

        //static class도 new로 해서 생성해야 한다.
        //외부 클래스가 A, 내부 일반 클래스가 B, 내부 static 클래스가 C라면
        //A생성 -> new A()
        //B생성 -> new A().new B()
        //C생성 -> new A.C()

        //static class도 new를 해서 생성 후 힙 영역에 올려줘야 객체를 사용할 수 있다.
        //실행시 static 영역으로 올라가서 사용 준비가 되는  static 변수와 static 메서드와 달리
        //static class는 생성해서 힙 영역에 올려준 뒤 사용한다.
        return new BoardResponse.DetailDTOV2(board, sessionUser);

    }
}
