package shop.mtcoding.blog.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

// 테스트할 클래스 이름은 클래스명 + Test를 붙이는 것이 약속이다.

// @SpringBootTest   // C R em h2  -> 모든 레이어를 메모리에 다 올리고 테스트 할 때 사용하는 어노테이션. insert 테스트라 지금 필요x
// C = Controller   R = Repository  em = Hibernate EntityManager   h2 = Database
@DataJpaTest    // h2, em
@Import(BoardRepository.class)  // R
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void deleteById() {
        //given
        int id = 1;


        //when
        int result = boardRepository.deleteById(id);


        //eye
        System.out.println(result);
    }





    @Test
    public void findById() {
        // given
        int id = 6;

        // when
        Board board = boardRepository.findById(id);

        // eye (then - assertion을 사용한다.)
        System.out.println(board);
    }


    @Test
    public void findAll_test() {
        // given

        // when
        List<Board> boardList = boardRepository.findAll();

        // eye(원래는 then이고 assertion-that?을 쓴다)
        System.out.println("사이즈: " + boardList.size());
        for (Board board : boardList) {
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
        }
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
        boardRepository.save(title, content);


        // eye (눈으로 확인)

    }


}
