package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserRepository {


    private final EntityManager em;

    public User findByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("select u from User u where u.username =:username and u.password=:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (User) query.getSingleResult();
    }


    @Transactional
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