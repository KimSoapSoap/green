package shop.mtcoding.springv3.core.error;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import shop.mtcoding.springv3.core.error.ex.Exception400;


@Component  //IoC빈 컨테이너에 등록시켜줌
@Aspect   //AOP 등록
public class GlobalValidationHanlder {


/*
    //특정 메서드나 어노테이션이 붙은 것이 실행 되기 전에 메서드 실행
    @Before("@annotation(shop.mtcoding.blog.core.Hello)")
    // 메서드 이름으로 찾아서 사용하기 보다 어노테이션으로 찾아서 사용한다. 풀패키지명을 적어준다.
    public void hello() {
        System.out.println("aop hello 호출됨");
    }
*/


    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    //만약 Post와 Put 둘 다 넣어주려면
    //@Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void validCheck(JoinPoint jp) {
        Object[] args = jp.getArgs(); // 매개변수를 가져온다.
        System.out.println("사이즈 : " + args.length);
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
                //@Valid에서 Null이나 공백이나 null이 나오면 매개변수 Errors errors로 넘기는 걸로 설정해놨으므로 arg가 Errors 타입이라면
                //유효성 검사가 실패한 검사다.

                if (errors.hasErrors()) {
                    //for문 돌려서 에러 뜬 거 하나만 띄워준다.
                    for (FieldError error : errors.getFieldErrors()) {
                        throw new Exception400(error.getDefaultMessage() + ":" + error.getField());

                    }

                }
            }
        }

    }

    //특정 메서드나 어노테이션이 붙은 것이 실행 되기 전과 후에 특정 코드를 실행
    @Around("@annotation(shop.mtcoding.blog.core.Hello)")
    // 메서드 이름으로 찾아서 사용하기 보다 어노테이션으로 찾아서 사용한다. 풀패키지명을 적어준다.
    public Object hello1(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("aop hello1 before 호출됨");
        Object proceed = jp.proceed();  // @Hello 어노테이션이 붙은 함수 호출 -> Throwable 해줘야됨
        System.out.println("aop hello1 after 호출됨");
        System.out.println(proceed);
        return proceed; //리턴을 해줘야 DispatcherServlet이 처리해줌
    }

}

