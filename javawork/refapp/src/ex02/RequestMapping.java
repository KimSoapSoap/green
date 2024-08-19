package ex02;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//어노테이션 만들기
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    //메서드 이름이 어노테이션의 키값의 이름이 된다.
    //변수x, 무조건 메서드로만 적을 수 있다.
    //String uri()로 하면 @RequestMapping(uri = String),   int uri()로 하면 @RequestMapping(uri = int)   키값은 uri
    //String value()로 하면 @RequestMapping(value = "/board")일 때 키값이 value면 생략 가능하다. @RequestMapping("/board")
    //String value() default "";  이렇게 한다면 key값이 null일 때 value값의 default 값으로 "" 설정
    String uri();
}

/*
실제 @Controller 모양
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}
*/


/*
       @Retention(RetentionPolicy.RUNTIME)
       옵션들
       RetentionPolicy.RUNTIME
       RetentionPolicy.CLASS
       RetentionPolicy.SOURCE

       @Retention 어노테이션은 어노테이션의 수명을 정의하는 데 사용됩니다. 즉, 어노테이션이 언제까지 유지될지를 결정합니다.
       RetentionPolicy는 어노테이션의 수명 주기를 설정하는 Retention 어노테이션의 속성입니다.

       RUMTIME - 어노테이션이 런타임 시점에도 유지됩니다. 이는 JVM이 실행 중일 때 어노테이션에 접근할 수 있게 합니다.
                 이 설정을 사용하면 리플렉션(Reflection)을 통해 런타임 중에 어노테이션의 정보를 읽고 사용할 수 있습니다.

       CLASS - 어노테이션이 컴파일 시점에 유지됩니다. 즉, 컴파일된 클래스 파일에는 어노테이션이 포함되지만, JVM 실행 중에는 접근할 수 없습니다.
               클래스 파일에 어노테이션 정보는 존재하지만, 런타임에는 사용할 수 없습니다.

       SOURCE - 어노테이션이 소스 코드에서만 유지됩니다. 즉, 컴파일 과정 중에 어노테이션이 클래스 파일로 변환되기 전에 제거됩니다.
                이 설정을 사용하면 컴파일된 클래스 파일에는 어노테이션이 포함되지 않으며, 런타임에도 접근할 수 없습니다.



   -------------------------------------------------------------------------------------------------------------

        @Target(ElementType.METHOD) //어노테이션을 붙일 수 있는 위치를 설정해준다
        옵션들
        ElementType.METHOD   //메서드
        ElementType.TYPE   // 클래스.  클래스는 CLASS가 아니라 TYPE이다.


@Target 어노테이션은 다른 어노테이션이 적용될 수 있는 Java 요소의 종류를 정의합니다.
ElementType 열거형을 사용하여 어노테이션이 적용될 수 있는 위치를 지정합니다.


ElementType.TYPE
클래스, 인터페이스(타입), 열거형 등 모든 타입에 적용될 수 있습니다.
용도: 클래스나 인터페이스에 어노테이션을 붙이려는 경우 사용합니다. 예를 들어, @Entity, @Controller 등.


ElementType.FIELD
설명: 클래스의 필드에 적용될 수 있습니다.
용도: 필드에 대한 메타데이터를 제공하는 데 사용됩니다. 예를 들어, @Autowired, @Value 등.


ElementType.METHOD
설명: 메서드에 적용될 수 있습니다.
용도: 메서드에 대한 메타데이터를 제공하는 데 사용됩니다. 예를 들어, @RequestMapping, @PostConstruct 등.


ElementType.PARAMETER
설명: 메서드의 매개변수에 적용될 수 있습니다.
용도: 매개변수에 대한 메타데이터를 제공하는 데 사용됩니다. 예를 들어, @RequestParam, @PathVariable 등.


ElementType.CONSTRUCTOR
설명: 생성자에 적용될 수 있습니다.
용도: 생성자에 대한 메타데이터를 제공하는 데 사용됩니다.

ElementType.LOCAL_VARIABLE
설명: 지역 변수에 적용될 수 있습니다.
용도: 지역 변수에 대한 메타데이터를 제공하는 데 사용됩니다.


ElementType.ANNOTATION_TYPE
설명: 다른 어노테이션을 정의할 때 적용될 수 있습니다.
용도: 어노테이션의 메타데이터를 정의하는 데 사용됩니다.


ElementType.PACKAGE
설명: 패키지에 적용될 수 있습니다.
용도: 패키지 수준의 메타데이터를 정의하는 데 사용됩니다.

 */


