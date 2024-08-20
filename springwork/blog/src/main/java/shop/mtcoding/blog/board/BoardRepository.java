package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Repository를 붙이면 스프링이 서버 시작시 new를 해서 생성한 뒤 IoC에 저장한다.
// (IoC라는 자료구조 컬렉션 리스트에 객체를 생성해서 참조변수를 저장해둔다. 꺼내쓰기 쉽게)
// 이렇게 스프링이 @Repository, @Controller, @Service 등의 어노테이션을 통해 생성해서 관리해주는 객체를 꺼내 쓰려면
// @Autowired 라는 어노테이션을 붙여서 사용한다.
// 스프링이 싱글톤 방식으로 생성해서 bean 컨테이너에 저장해두고 사용하는 것.(내가 내용 추가)
@Repository
public class BoardRepository {

    @Autowired  //IoC에 있는 객체를 찾아 온다. (EntityManager도 생성되어 IoC에 떠 있는 상태)
    private EntityManager em;

    @Transactional
    public void updatdById(String title, String content, int id) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);

        query.executeUpdate();
    }


    @Transactional
    public int deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id= ?");
        query.setParameter(1, id);
        try {
            int result = query.executeUpdate();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("게시글 id를 찾을 수 없습니다.");
        }


    }


    public Board findById(int id) {
        //두 번째 전달자 Board.class는 조회할 때만 해당 클래스의 정보를 담아서 가지고 오기 위해 필요하다. 수정 삭제에는 불필요
        Query query = em.createNativeQuery("select * from board_tb where id =?", Board.class);
        query.setParameter(1, id);
        //query.getSingleResult()는 Object를 리턴하기 때문에 (Board)로 다운캐스팅 해준다.
        //없는 게시글 번호가 들어가면 NoResultException이 터지기 때문에 예외처리를 해준다.
        //우리가 예외를 터뜨리고 나중에 예외관리를 해준다.
        try {
            Board board = (Board) query.getSingleResult();
            return board;
        } catch (Exception e) {
            // Exception을 내가 잡은 것 까지 배움 - 처리 방법은 v2에서 배울 예정. 
            // throw는 나를 호출한 녀석한테 예외를 던지는 것이다. 이렇게 한 곳으로 몰아 놓고 처리하면 깔끔하다
            // Spring에서는 DispatcherServlet에서 전체적으로 처리하는 방식으로 해보자.
            // 참고로  try - catch는 직접 처리하는 것. 계속 던지다 보면 최종적으로는 JVM이 처리
            throw new RuntimeException("게시글 id를 찾을 수 없습니다.");
        }
    }

    //select all
    public List<Board> findAll() {
        //기존 JDBC는 resultSet으로 select한 데이터를 받아줘야 하는데 여기선 필요 없다.
        //여기 두 번째 전달자에 들어갈 수 있는 클래스는 @Entity가 붙어서 관리되는 클래스만 넣어줄 수 있다.
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);

        //getSingleResult()는 primary key로 1건의 데이터를 검색할 때
        List<Board> boardList = query.getResultList();
        return boardList;
    }


    // insert 하기
    @Transactional  //동시 요청이 발생했을 때 누가 insert 트랜잭션을 실행중일 때 insert 하지 못하며 중간에 read 요청이 왔을 때는 트랜잭션 이전의 데이터를 보여주는 것
    public void save(String title, String content) {
        //em이 쿼리를 db에 전송
        Query query = em.createNativeQuery("insert into board_tb(title, content, created_at) values(?,?,now())");
        query.setParameter(1, title);
        query.setParameter(2, content);

        query.executeUpdate();
    }


}

