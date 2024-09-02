package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.mtcoding.blog.core.error.ex.Exception401;

@RequiredArgsConstructor
@Repository
public class UserRepository {


    private final EntityManager em;

    public User findByUsername(String username) {
        Query query = em.createQuery("select u from User u where u.username =:username ", User.class);
        query.setParameter("username", username);

        try {
            //찾으면 반환
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            //회원가입시 username으로 검색하고 존재하지 않으면(null) 사용할 수 있는 username이므로 가입 가능하다.
            //그렇기 때문에 이때는 null을 반환해야 회원가입 메서드에서 null을 받고 회원가입 로직을 진행한다.
            //예외를 던지면 안 된다

            return null;
        }

    }


    public User findByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("select u from User u where u.username =:username and u.password=:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            //여기서 e.getMessage() 해주면 클라이언트한테 에러메시지가 노출된다. (아디가 틀렸는지 비번이 틀렸는지. 좋지 않다)
            throw new Exception401("인증되지 않았습니다.");
        }

    }


    public void save(User user) {
        //Timestamp now = Timestamp.valueOf(LocalDateTime.now());

//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);

        //user.setCreatedAt(now);

        System.out.println("담기기 전: " + user.getId());  // 비영속 객체일 때
        em.persist(user);
        System.out.println("담기기 후: " + user.getId()); // db로 들어가는 영속 객체가 됨 (primary key인 id를 부여받음)
    }
}