package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

//값만 들고 있는 value Object
//db Table 데이터와 똑같은 녀석. 테이블 생성을 위한 클래스이다.
@NoArgsConstructor  // Lombok 라이브러리에서 제공하는 기능으로, 클래스의 기본 생성자를 자동으로 생성해준다
@Setter  // Lombok 라이브러리에서 제공하는 기능으로 모든 필드에 대한 Setter를 자동으로 생성해준다.
@Getter // Lombok 라이브러리에서 제공하는 기능으로 모든 필드에 대한 Getter를 자동으로 생성해준다.
@Table(name = "board_tb")   //이걸 안 써주면 테이블 이름이 클래스 이름으로 들어가서 위험. table이름 끝에는 _tb 붙이면 굿
// DB에서 조회하면 자동 매핑이 됨. Hibernate는 @Entity 어노테이션이 붙은 클래스를 스캔하여, 해당 클래스가 데이터베이스 테이블과 매핑되는 엔티티(클래스)임을 인식.
// 이로써 해당 클래스를 데이터베이스 작업에 사용할 수 있다..
@Entity
public class Board {

    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto_increment 설정. 아래 변수에 붙는 것. 시퀀스 설정->1씩 상승(db에 넣을 때)
    @Id     // PK설정.  primary key 설정, index 설정
    private Integer id;
    @Column(nullable = false)     // 컬럼에 null값을 허용하지 않는 것. table이 생성될 때 이 어노테이션이 붙은 컬럼은 NOT NULL 속성을 가진다
    private String title;
    @Column(nullable = false)
    private String content;
    private Timestamp createdAt;


    //빌더패턴 적용. 필요한 멤버변수 모두 선택한 후 생성자 만들어서 @Builder 붙인다.
    @Builder
    public Board(Integer id, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

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
/*
    빈 생성자의 필요성
    Hibernate와 같은 JPA 구현체는 데이터베이스로부터 엔티티 객체를 생성할 때, 리플렉션을 통해 기본 생성자(파라미터가 없는 생성자)를 사용합니다.
	이 기본 생성자가 없으면 Hibernate는 객체를 제대로 생성할 수 없고, 결과적으로 애플리케이션이 제대로 동작하지 않을 수 있습니다.
	기본 생성자를 통해 객체를 생성한 후, 리플렉션을 사용해 필드에 값을 설정하기 때문에 기본 생성자가 반드시 존재해야 합니다.
	따라서 빈 생성자(기본 생성자)는 JPA 엔티티 클래스에서 필수적입니다. 이를 위해 클래스에 직접 빈 생성자를 추가하거나
	@NoArgsConstructor 어노테이션을 사용하여 기본 생성자를 자동으로 생성하도록 합니다.


 */


