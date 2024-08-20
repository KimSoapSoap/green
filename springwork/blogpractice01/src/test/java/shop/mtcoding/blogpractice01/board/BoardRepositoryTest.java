package shop.mtcoding.blogpractice01.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.blogpractice01.board.Board;
import shop.mtcoding.blogpractice01.board.BoardRepository;

import java.util.List;

@DataJpaTest
@Import(BoardRepository.class)
public class BoardRepositoryTest {


    @Autowired
    BoardRepository boardRepository;


    @Test
    void delete() {
        // given
        int id = 1;
        String title = "제목";
        String content = "내용";

        // when
        boardRepository.save(title, content);
        boardRepository.delete(id);

        // then
        try {
            Board board = boardRepository.findById(id);
        }catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("해당 id의 게시글을 찾을 수 없습니다.");

        }
    }


    @Test
    void update() {
        // given
        int id = 1;
        String title = "제목1수정";
        String content = "내용1수정";

        // when
        boardRepository.save("123", "456");
        boardRepository.update(id, title, content);

        // then
        Assertions.assertThat(boardRepository.findById(id).getTitle()).isEqualTo(title);
        Assertions.assertThat(boardRepository.findById(id).getContent()).isEqualTo(content);
    }

    @Test
    void findById() {
        // given
        int id = 1;
        String title = "제목1";
        String content ="내용1";

        // when
        boardRepository.save(title, content);
        Board board = boardRepository.findById(id);


        // then
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
        Assertions.assertThat(board.getContent()).isEqualTo("내용1");
    }



    @Test
    void findAll() {
        // given


        // when
        List<Board> list = boardRepository.findAll();


        //eye
        for(Board b: list) {
            System.out.println(b);
        }


        // then

    }


    @Test
    void save_test() {
        // given
        String title = "제목1";
        String content = "내용1";


        // when
        boardRepository.save(title, content);


        // then
        //findById() 아직 안 만들었고 findAll()만 만든 상태에서 1개 넣고 0번 꺼내서 확인
        List<Board> list = boardRepository.findAll();
        Assertions.assertThat(list.get(0).getTitle()).isEqualTo("제목2");
    }



}

