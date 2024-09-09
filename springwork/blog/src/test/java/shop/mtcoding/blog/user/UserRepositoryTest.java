package shop.mtcoding.blog.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;

@DataJpaTest    // h2, em
@Import(UserRepository.class)  // R
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    //테스트에서 사용하는 request, response, session 등은 앞에 Mock을 붙인 테스트용 클래스가 존재한다.
    MockHttpSession session = new MockHttpSession();


    @Test
    public void findByUsername_test() {
        //given
        String username = "haha";


        //when
        User user = userRepository.findByUsername(username);


    }

    @Test
    public void findByUsernameAndPassword_test() {
  /*      Query query = em.createQuery("select u from User u where u.username =:username and u.password=:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (User) query.getSingleResult();*/
    }


    @Test
    public void login_test() {
        //given
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";

        User user = User.builder().username(username).password(password).email(email).build();
        userRepository.save(user);
        User sessionUser = userRepository.findByUsernameAndPassword(username, password);
        System.out.println(sessionUser);

        //when
        session.setAttribute("sessionUser", sessionUser);


        //then
        Assertions.assertThat(session.getAttribute("sessionUser")).isNotNull();
    }


    @Test
    public void save_test() {
        // given
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";

        //when
        User user = User.builder().username(username).password(password).email(email).build();
        userRepository.save(user);


        //then
        Assertions.assertThat(userRepository.findByUsernameAndPassword(username, password)).isEqualTo(user);

    }

}
