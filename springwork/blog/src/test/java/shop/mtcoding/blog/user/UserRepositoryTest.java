package shop.mtcoding.blog.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest    // h2, em
@Import(UserRepository.class)  // R
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_test() {
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";

        userRepository.save(username, password, email);
    }

}
