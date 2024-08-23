package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception403;
import shop.mtcoding.blog.user.User;

import java.util.List;

//Controller -> Service -> Repository

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    public List<Board> 게시글목록보기() {
        //여기서 페이징 처리한다.
        return boardRepository.findAll();
    }

    @Transactional
    public void 게시글수정(String title, String content, int id) {
        //유효성 검사, null뜨면 repository의 findById()에서 처리
        Board board = boardRepository.findById(id);


        boardRepository.updatdById(title, content, id);

    }

    public Board 게시글수정화면(int id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public void 게시글삭제(int id, User sessionUser) {
        //1.컨트롤러로 부터 게시글 id를 받기
        // -> 메서드 파라미터로 완료

        //2. db에 게시글 존재여부 확인 (없으면 404에러 던지는데 findById()에서 처리해둠)
        Board board = boardRepository.findById(id);

        //3. 내가 쓴 글인지 확인하기 (403에러. 권한없음)
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("작성자가 아닙니다.");
        }

        //4. 게시글 삭제
        boardRepository.deleteById(id);
    }


    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        boardRepository.save(saveDTO.toEntity(sessionUser));
    }


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
