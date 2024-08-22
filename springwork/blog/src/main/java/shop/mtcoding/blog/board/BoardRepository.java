package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
import java.util.List;

// @Repository를 붙이면 스프링이 서버 시작시 new를 해서 생성한 뒤 IoC에 저장한다.
// (IoC라는 자료구조 컬렉션 리스트에 객체를 생성해서 참조변수를 저장해둔다. 꺼내쓰기 쉽게)
// 이렇게 스프링이 @Repository, @Controller, @Service 등의 어노테이션을 통해 생성해서 관리해주는 객체를 꺼내 쓰려면
// @Autowired 라는 어노테이션을 붙여서 사용한다.
// 스프링이 싱글톤 방식으로 생성해서 bean 컨테이너에 저장해두고 사용하는 것.(내가 내용 추가)
@RequiredArgsConstructor  // final이 붙은 멤버 변수들을 생성자 주입 해준다.
@Repository
public class BoardRepository {


    private final EntityManager em; //IoC에 있는 객체를 찾아 온다. (EntityManager도 생성되어 IoC에 떠 있는 상태)


    @Transactional
    public void updatdById(String title, String content, int id) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);

        query.executeUpdate();
    }

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
<<<<<<< HEAD
        Query query = em.createNativeQuery("select * from board_tb where id =?", Board.class);
        query.setParameter(1, id);
=======
        //nfd 순서로 1:n 관계에서 n인 녀석을 먼저하고 foreign 키를 뒤에 join.
        //board_tb의 user_id와 user_tb의 id를 일치시켜서 join시킨다. user_tb의 id가 board_tb에서 외래키로 user_id로 사용됐으므로.
        //Board가 멤버변수로 User를 가지고 있기 때문에 따로 User.class로 맵핑 해주지 않아도 된다.
        //원래 쿼리 : select * from board_tb bt inner join user_tb ut on bt.user_id = ut.id where ut.id = 1
        //원래 쿼리는 오브젝트 릴레이션 맵핑이 안 된다. 그래서 JPQL 사용.

        // JPA가 제공하는 쿼리(JPQL). select b from Board b가 board_tb 가져오는 것. 그냥 join fetch는 inner join. left join fetch는 left outer join
        // JPQL은 ?를 쓰지 않고 :variable을 쓴다. 그리고 qeury.setParameter("id", id)로 해준다.
        // Board b는 Board객체를 별칭으로 b로 했고  b.user는 User객체를 가리킨다. 이 b.user를 u라는 별칭으로 이용해서 Board와 User를 inner join
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id", id);
//        Query query = em.createQuery("select b from Board b where b.id =:id", Board.class);
//        query.setParameter("id", id);
>>>>>>> sub
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
            e.printStackTrace();  //오류 추적
            throw new RuntimeException("게시글 id를 찾을 수 없습니다.");
        }
    }

    //select all
    public List<Board> findAll() {
        //기존 JDBC는 resultSet으로 select한 데이터를 받아줘야 하는데 여기선 필요 없다.
        //여기 두 번째 전달자에 들어갈 수 있는 클래스는 @Entity가 붙어서 관리되는 클래스만 넣어줄 수 있다.

        //원래 쿼리 : "select * from board_tb order by id desc"

        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);

        //getSingleResult()는 primary key로 1건의 데이터를 검색할 때
        List<Board> boardList = query.getResultList();
        return boardList;
    }


    // insert 하기
    @Transactional  //동시 요청이 발생했을 때 누가 insert 트랜잭션을 실행중일 때 insert 하지 못하며 중간에 read 요청이 왔을 때는 트랜잭션 이전의 데이터를 보여주는 것
    public void save(Board board) {
        //em이 쿼리를 db에 전송

//        Query query = em.createNativeQuery("insert into board_tb(title, content, created_at) values(?,?,now())");
//        query.setParameter(1, title);
//        query.setParameter(2, content);
//        query.executeUpdate();

        em.persist(board);
    }


    //아래부터 V2_1~V2_3은 blog V2에 BoardRepository에서 createNativeQuery에 두번 째 전달자로 *.class를 추가하지 않으면 오브젝트 맵핑을 직접 해야하는 예시


    public Board findByIdV2_1(int id) {
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.user_id, bt.created_at, ut.id u_id, ut.username, ut.password, ut.email, ut.created_at u_created_at from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);
        Object[] obs = (Object[]) query.getSingleResult();
        System.out.println(obs[0]);
        System.out.println(obs[1]);
        System.out.println(obs[2]);
        System.out.println(obs[3]);
        System.out.println(obs[4]);
        System.out.println(obs[5]);
        System.out.println(obs[6]);
        System.out.println(obs[7]);
        System.out.println(obs[8]);
        System.out.println(obs[9]);
        return null;
    }


    public Board findByIdV2_2(int id) {
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.user_id, bt.created_at, ut.id u_id, ut.username, ut.password, ut.email, ut.created_at u_created_at from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);
        Object[] obs = (Object[]) query.getSingleResult();
//        1
//        제목1
//        내용1
//        1
//        2024-08-21 12:49:35.197432
//        1
//        ssar
//        1234
//        ssar@nate.com
//        2024-08-21 12:49:35.194432
        Board board = new Board();
        board.setId((Integer) obs[0]);
        //board.setTitle();
        System.out.println(obs[0]);
        System.out.println(obs[1]);
        System.out.println(obs[2]);
        System.out.println(obs[3]);
        System.out.println(obs[4]);
        System.out.println(obs[5]);
        System.out.println(obs[6]);
        System.out.println(obs[7]);
        System.out.println(obs[8]);
        System.out.println(obs[9]);
        return null;
    }

    //오브젝트 릴레이션 맵핑 직접 한 것.
    public Board findByIdV2_3(int id) {
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.user_id, bt.created_at, ut.id u_id, ut.username, ut.password, ut.email, ut.created_at u_created_at from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);
        Object[] obs = (Object[]) query.getSingleResult();

        System.out.println(obs[0]);
        System.out.println(obs[1]);
        System.out.println(obs[2]);
        System.out.println(obs[3]);
        System.out.println(obs[4]);
        System.out.println(obs[5]);
        System.out.println(obs[6]);
        System.out.println(obs[7]);
        System.out.println(obs[8]);
        System.out.println(obs[9]);

//        1
//        제목1
//        내용1
//        1
//        2024-08-21 12:49:35.197432
//        1
//        ssar
//        1234
//        ssar@nate.com
//        2024-08-21 12:49:35.194432
        Board board = new Board();
        User user = new User();
        board.setId((Integer) obs[0]);
        board.setTitle((String) obs[1]);
        board.setContent((String) obs[2]);
        board.setCreatedAt((Timestamp) obs[4]);

        user.setId((Integer) obs[3]);
        user.setUsername((String) obs[6]);
        user.setPassword((String) obs[7]);
        user.setEmail((String) obs[8]);
        user.setCreatedAt((Timestamp) obs[9]);

        board.setUser(user);

        return board;
    }

}

