package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Table(name = "user_tb")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1씩 증가하는 설정
    private Integer id;
    @Column(unique = true, nullable = false)
    private String username;    //아이디
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    private Timestamp createdAt;


    //빌더는 만들어 두는 것이 좋다
    @Builder
    public User(Integer id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
}
