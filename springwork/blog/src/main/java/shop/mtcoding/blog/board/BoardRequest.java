package shop.mtcoding.blog.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {
    //요청 DTO는 동일하게 생겨도 중복해서 만들어 줘야한다.

    @Data
    public static class UpdateDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
    }


    @Data
    public static class SaveDTO {
        @NotEmpty //공백x Null x
        private String title;
        @NotEmpty
        private String content;


        //insert 할 때는 toEntity()를 만들어 준다. -> 영속화
        public Board toEntity(User sessionUser) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(sessionUser)
                    .build();
        }
    }


}
