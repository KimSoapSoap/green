package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class UserRepository {

    @Autowired
    private EntityManager em;

    @Transactional
    public void save(String username, String password, String email) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCreatedAt(now);

        em.persist(user);
    }
}