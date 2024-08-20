package shop.mtcoding.blogpractice01.board;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Table(name ="board_tb")
@Entity
@Data
public class Board {
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto_increment 설정. 아래 변수에 붙는 것. 시퀀스 설정->1씩 상승(db에 넣을 때)
    @Id
    private int id;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private String content;
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
