package org.example.springv3.reply;

//요청시에 필요한 정보를 OORequest클래스를 만들고 OODTO를 만들어서 사용.
//또한 응답시에 전달할 정보를 Entity로 그대로 전달하지 말고 OOResponse클래스를 만들고 OODTO를 만들어서 사용
public class ReplyResponse {

    public static class DTO {

        private Integer id;
        private String comment;
        private String username;


        public DTO(Reply reply) {
            this.id = reply.getId();
            this.comment = reply.getComment();
            //만약 User정보가 없다 해도 getter 호출하니까 LAZY로딩 일어나서 정보를 가져 온다.
            this.username = reply.getUser().getUsername();



        }

        @Override
        public String toString() {
            return "DTO{" +
                    "id=" + id +
                    ", comment='" + comment + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }
}
