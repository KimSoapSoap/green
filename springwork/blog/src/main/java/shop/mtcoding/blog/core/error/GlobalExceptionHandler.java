package shop.mtcoding.blog.core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//모든 Exception이 여기로 날아오는 어노테이션
@RestControllerAdvice
public class GlobalExceptionHandler {

    //@ExceptionHandler(A.class)  -> A예외를 처리한다
    @ExceptionHandler(RuntimeException.class)
    public String ex(Exception e) {

        // Exception이 예외의 최상위 부모이므로 모든 예외를 다 잡는다.
        // 예외가 터지면 Exception e를 받아서 설정해둔 에러메시지를 받아서 사용
        String errMsg = """                
                <script>
                    alert('$msg'); 
                    history.back();
                </script>
                """.replace("$msg", e.getMessage());

        // 자바스크립트로 우리가 정한 에러메시지로 alert를 띄워준다.
        // 자바스크립트의 history.back()은 뒤로가기
        return errMsg;


    }
}
