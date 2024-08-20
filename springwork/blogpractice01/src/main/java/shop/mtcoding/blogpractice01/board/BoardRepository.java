package shop.mtcoding.blogpractice01.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BoardRepository {

    @Autowired
    EntityManager em;

    @Transactional
    public void delete(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();

        query.executeUpdate();
    }


    @Transactional
    public void update(int id, String title, String content) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);

        query.executeUpdate();
    }


    public Board findById(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        query.setParameter(1, id);
        try {
            return (Board) query.getSingleResult();
        } catch(NoResultException e) {
            throw new RuntimeException("해당 id의 게시글을 찾을 수 없습니다.");
        }

    }


    @Transactional
    public void save(String title, String content) {
        Query query = em.createNativeQuery("insert into board_tb(title, content, created_at) values (?, ?, now())");
        query.setParameter(1, title);
        query.setParameter(2, content);

        query.executeUpdate();
    }


    public List<Board> findAll() {
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);
        List<Board> list = query.getResultList();
        return list;
    }

}
