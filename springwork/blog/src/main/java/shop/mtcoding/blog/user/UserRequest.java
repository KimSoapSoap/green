package shop.mtcoding.blog.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

public class UserRequest {

    //요청 body 데이터를 편하게 받기 위해 만들었다.
    @Data  // @Getter, @Setter, @ToString.. 등
    public static class JoinDTO {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
        private String email;

        //이 메서드의 책임은 DTO -> UserObject로 바꿔주는 책임
        //toEntity()는 insert 할 때만 필요. select 할 때는 만들어줄 필요 없다.
        public User toEntity() {
            return User.builder().username(username).password(password).email(email).build();
        }
    }

    @Data
    public static class LoginDTO {
        @Length(min = 2, max = 12)
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }
}
