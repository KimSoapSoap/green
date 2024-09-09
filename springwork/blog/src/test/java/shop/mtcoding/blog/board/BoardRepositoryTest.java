package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.blog.core.error.ex.Exception403;
import shop.mtcoding.blog.core.error.ex.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

// 테스트할 클래스 이름은 클래스명 + Test를 붙이는 것이 약속이다.

// @SpringBootTest   // C R em h2  -> 모든 레이어를 메모리에 다 올리고 테스트 할 때 사용하는 어노테이션. insert 테스트라 지금 필요x
// C = Controller   R = Repository  em = Hibernate EntityManager   h2 = Database
@DataJpaTest    // h2, em
@Import(BoardRepository.class)  // R
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void updateByIdV2_Test() {
        //given
        int id = 1;
        Board board = boardRepository.findById(id);

        //when
        board.setTitle("제목10");
        board.setContent("내용10");


        //트랜잭션이 commit되면 flush()를 날려서 영속 객체에 변경사항이 있으면(dirty checking) 쿼리를 날린다.
        //em.persist하거나 em.find로 꺼내면 영속 컨텍스트에 저장돼서 영속 객체상태이다
        //처음 영속 객체로 저장될 때의 상태를 스냅샷에 정보를 저장하는데 flush()를 할 때 스냅샷에 저장된 정보와
        //현재 영속 객체의 정보를 비교해서 변경사항이 있으면
        // persist로 영속화 된 객체는 변경된 녀석으로 insert쿼리가 날아가고
        // 조회해서 영속화 된 객체는 변경된 녀석으로 update쿼리가 날아간다.
        em.flush();

    }


    @Test
    public void 게시글삭제() {
        //given
        int id = 1;
        User sessionUser = User.builder().id(1).build();

        //when
        //db에 게시글 존재여부 확인 (없으면 404에러 던짐)
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new Exception404("존재하지 않는 게시글입니다.");
        }
        //내가 쓴 글인지 확인하기 (403에러. 권한없음)
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("작성자가 아닙니다.");
        }
        //게시글 삭제
        boardRepository.deleteById(id);
        //then
        try {
            boardRepository.findById(id);
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("게시글 id를 찾을 수 없습니다.");
        }
    }

    @Test
    public void updateById_test() {
        // given
        int id = 1;
        String title = "제목1변경";
        String content = "내용1변경";

        // when
        boardRepository.updatdById(title, content, id);


        // then
        Board board = boardRepository.findById(id);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목2변경");
    }

    @Test
    public void deleteById_test() {
        //given
        int id = 1;

        //when
        boardRepository.deleteById(id);

        //then
        try {
            //삭제됐으면 찾을 수 없기에 findById(Int id)에 우리가 만들어 둔 예외를 던짐
            boardRepository.findById(id);
        } catch (Exception e) {
            //검색한 것을 찾을 수 없을 때 던지도록 만들어 둔 예외를 잡아서 catch구문으로 내려오고 에러코드의 메시지 일치를 검증한다.
            //오타를 낼 수 있으니 상수처리 하거나 ENUM으로 해주면 안정성이 높아 진다
            Assertions.assertThat(e.getMessage()).isEqualTo("게시글 id를 찾을 수 없습니다.");
        }
    }


    @Test
    public void findById_test() {
        // given
        int id = 1;

        // when
        Board board = boardRepository.findById(id);

        // eye (then - assertion을 사용한다.)
        System.out.println(board);


        //then (코드로 검증 -> eye 대신 사용. 일단 eye는 남겨놨다)
        //Assertions는 assertj를 사용한다.(기능이 더 많음)
        //Aseertions.assertThat(A).isEqualTo(B)   -> A와 B가 일치하기를 기대한다.
        //모든 필드에 대해 다 한다
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");
    }


    @Test
    public void findAll_test() {
        // given

        // when
        System.out.println("1. 첫번째 조회");

        List<Board> boardList = boardRepository.findAll();
        System.out.println(("userId : " + boardList.get(0).getUser().getId()));
        System.out.println(("userId : " + boardList.get(0).getUser().getId()));
        System.out.println("=================================");


        // eye
        System.out.println("2. 레이지 로딩");
        System.out.println("username : " + boardList.get(0).getUser().getUsername());
        System.out.println("username : " + boardList.get(1).getUser().getUsername());
        System.out.println("username : " + boardList.get(2).getUser().getUsername());
        System.out.println("username : " + boardList.get(3).getUser().getUsername());
        System.out.println("username : " + boardList.get(3).getUser().getUsername());
        System.out.println("username : " + boardList.get(3).getUser().getUsername());


    }


    // 메서드명_test : 컨벤션.   메서드 이름을 끝에 _test 붙여서 만드는 것이 약속이다. methodName_test
    // given,  when,  eye로 나눈다.
    // 테스트 메서드에서는 매개변수를 사용할 수 없다.
    @Test
    public void save_test() {
        // given
        // given에 적는 것이 매개변수로 사용할 변수들을 만드는 것이다.
        String title = "제목1";
        String content = "내용1";


        // when
        //여기서 테스트한다.
        // boardRepository.save(title, content);


        // eye (눈으로 확인)


        //then

    }


}
