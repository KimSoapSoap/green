package shop.mtcoding.springv3.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


//인터페이스는 인터페이스를 상속받을 수 있다. JPaRepository<T, id> 여기서 T는 Type, id는 pk값이다.
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username =:username")
    User findByUsername(@Param("username") String username);

    //네이티브 쿼리 사용하려면
    //@Query("select u from User u where u.username =:username", nativeQuery=true?)
    //근데 이거 말고 그냥 UserRepository 하나 더 만들면 된다.

}
