package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;


        //insert 할 때는 toEntity()를 만들어 준다.
        public Board toEntity(User sessionUser) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(sessionUser)
                    .build();
        }

    }

}
