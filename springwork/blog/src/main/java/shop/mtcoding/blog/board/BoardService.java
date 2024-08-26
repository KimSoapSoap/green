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
    public void 게시글수정(int id, BoardRequest.UpdateDTO updateDTO, User sessionUser) {
        //유효성 검사, null뜨면 repository의 findById()에서 처리
        // 1. 게시글 조회 (없으면 404)
        Board board = boardRepository.findById(id);
        //em.find로 찾은 것이 아니어도 영속성 컨텍스트에 들어가나?

        // 2. 권한 체크
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        // 3. 게시글 수정
        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());

        //트랜잭션이 commit되면 flush()를 날려서 영속 객체에 변경사항이 있으면(dirty checking) 쿼리를 날린다.
        //em.persist하거나 em.find로 꺼내면 영속 컨텍스트에 저장돼서 영속 객체상태이다
        //처음 영속 객체로 저장될 때의 상태를 스냅샷에 정보를 저장하는데 flush()를 할 때 스냅샷에 저장된 정보와
        //현재 영속 객체의 정보를 비교해서 변경사항이 있으면
        // persist로 영속화 된 객체는 변경된 녀석으로 insert쿼리가 날아가고
        // 조회해서 영속화 된 객체는 변경된 녀석으로 update쿼리가 날아간다.

        //한 트랜잭션 과정 안에서 무언가를 조회했을 때 찾는 녀석이 영속성 컨텍스트에 저장돼 있으면 db에서 조회하지 않고 저장된 걸 반환한다
        //em.find()로 조회해야 db에서 꺼낸 녀석을 영속성 컨텍스트에 저장하고 그냥 repository에서 직접 꺼냈을 때는 영속성 컨텍스트에 저장되지 않는다

    }

    public Board 게시글수정화면가기(int id, User sessionUser) {
        Board board = boardRepository.findById(id);

        //수정할 권한이 있는지 권한체크
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        return board;
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
