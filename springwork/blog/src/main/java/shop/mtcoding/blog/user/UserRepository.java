package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

    @Autowired
    private EntityManager em;

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