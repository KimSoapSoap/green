package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardResponse {

    //상세보기를 위한 DTO V2
    //화면에 필요한 모든  정보를 담는다.
    //V1에서는 정보의 계층없이 그냥 동등하게 담았다면
    //V2에서는 외래키은 User테이블의 정보는 UserDTO를 내부에 만들어서 담아줬다.
    @Data
    public static class DetailDTOV2 {
        private Integer id;
        private String title;
        private String content;
        private Boolean isOwner;
        private UserDTOV2 user;


        @Data
        public class UserDTOV2 {
            private Integer id;
            private String username;

            //모든 필드 생성자를 만들어서 User user로  받고 user에서 꺼내서 생성자에서 멤버 변수에 넣어줌
            public UserDTOV2(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }
    }


    //상세보기를 위한 DTO
    //규칙
    //화면에 보이는 정보와 필요한 정보(권한 등)는 반드시 전달한다.
    //화면에 보이지 않더라도 primary key는 반드시 담는다. 나중에 어떻게 가공할지 모르기 때문에
    //다른 테이블의 정보도 일단은 구별없이 동등하게 다 담아준다.
    @Data
    public static class DetailDTO {
        private Integer boardId;
        private String title;
        private String content;
        private Boolean isOwner;

        private Integer userId;
        private String username;


        //모든 필드 생성자를 만들어서 Board board로 바꿔 넣고 board에서 꺼낸다.   엔티티에 있는 것을 DTO에 가져와서 사용
        public DetailDTO(Board board, User sessionUser) {
            this.boardId = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.isOwner = false;

            if (board.getUser().getId() == sessionUser.getId()) {
                this.isOwner = true;
            }

            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();

        }
    }


}



