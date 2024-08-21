package shop.mtcoding.blog.user;

import lombok.Data;

public class UserRequest {

    //요청 body 데이터를 편하게 받기 위해 만들었다.
    @Data  // @Getter, @Setter, @ToString.. 등
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        //이 메서드의 책임은 DTO -> UserObject로 바꿔주는 책임
        //toEntity()는 insert 할 때만 필요. select 할 때는 만들어줄 필요 없다.
        public User toEntity() {
            return User.builder().username(username).password(password).email(email).build();
        }
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
