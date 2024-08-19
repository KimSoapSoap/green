package ex02;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        String path = "/board/save-form";

        UserController uc = new UserController();

        //A.getClass()  -> 생성된 A객체의 클래스의 정보를 가져옴.
        //getClass()는 객체가 인스턴스화된 후에만 사용할 수 있습니다.

        // 클래스의 정보를 가져와서 getDeclaredMethods()로 클래스의 선언된 모든 메서드들을 리턴. 복수형이므로 Method[]으로 받는다.
        // 메서드 리턴 순서는 랜덤이다. 객체의 클래스 정보에서 모든 메서드 정보를 가져왔다.
        Method[] methods = uc.getClass().getDeclaredMethods();


        for(Method mt : methods) {
            //mt.getDeclaredAnnotation(RequestMapping.class)을 호출하여 mt(메서드)에 선언된 어노테이션을 리턴
            //RequestMapping.class는 어노테이션 객체를 의미하고 이 어노테이션을 찾는 것이다.

            Annotation anno = mt.getDeclaredAnnotation(RequestMapping.class);

            //RequestMapping의 부모가 Annotation 이라서 RequestMapping 붙인 것은 RequestMapping으로 다운캐스팅 가능.
            RequestMapping rm = (RequestMapping) anno;

            //그냥 Annotation으로 안 받고 RequestMapping으로 받으면 안 되나?
            //Annotation 타입으로 받는 경우는 주로 특정 어노테이션이 어떤 종류의 어노테이션인지 모를 때, 혹은 여러 다른 종류의 어노테이션을 처리할 때 사용.
            //여기서는 명확하게 @RequestMapping 어노테이션을 찾고 있으므로, 처음부터 RequestMapping 타입으로 받는 것이 더 직관적이고 코드도 간결.

            //RequestMapping이 붙어 있지 않은 not found 처리
            if(rm == null) {
                System.out.println("404 not found");
                break;
            }

            //메서드에 붙은 rm의 uri와 path 일치 확인 후 일치하면 메서드 실행
            if(rm.uri().equals(path)) {
                //invoke : 부르는 것
                //uc객체를 불러서 method 실행
                mt.invoke(uc);
            }

        }


    }
}








        /*

A.getClass()	getClass()는 생성된 객체의 클래스 정보를 반환합니다. 객체의 런타임 클래스에 대한 메타데이터를 조회할 수 있지만, 객체가 생성되지 않은 상태에서는 사용할 수 없습니다.
getClass()는 객체가 인스턴스화된 후에만 사용할 수 있습니다.
즉, null 객체나 생성되지 않은 객체에는 getClass()를 호출할 수 없습니다. 예를 들어, null 참조에 getClass()를 호출하면 NullPointerException이 발생합니다.

A.getClass().getName() 이런식으로 사용하거나
Class<?> clazz = A.getClass();  해서  clazz.getName() 이렇게 사용한다. (변수명 class사용 불가해서 clazz로)
Example example = new Example();
Class<?> clazz = example.getClass();  라고 한다면

클래스 이름	getName() 메서드를 사용하여 클래스의 완전한 이름(패키지 포함)을 문자열 형태로 가져올 수 있습니다.
String name = clazz.getName();
클래스의 슈퍼클래스	getSuperclass() 메서드를 사용하여 클래스의 직접 상위(superclass)를 가져올 수 있습니다.
Class<?> superclass = clazz.getSuperclass();

클래스의 특정 메서드	Method methodSayHello = clazz.getMethod("sayHello");      // sayHello() 메서드를 가져옵니다.
Method methodGreet = clazz.getMethod("greet", String.class);      // greet(String) 메서드를 가져옵니다. greet 메서드는 String 타입의 매개변수를 가지므로 String.class를 지정합니다.

클래스가 구현하는 모든 인터페이스	getInterfaces() 메서드를 사용하여 클래스가 구현하는 모든 인터페이스를 배열 형태로 가져올 수 있습니다.
Class<?>[ ] interfaces = clazz.getInterfaces();
클래스의 모든 필드	getDeclaredFields() 메서드를 사용하여 클래스에 선언된 모든 필드를 가져올 수 있습니다.
Field[ ] fields = clazz.getDeclaredFields();
클래스의 모든 메서드	getDeclaredMethods() 메서드를 사용하여 클래스에 선언된 모든 메서드를 가져올 수 있습니다.
Method[ ] methods = clazz.getDeclaredMethods();
클래스의 모든 생성자	getDeclaredConstructors() 메서드를 사용하여 클래스에 선언된 모든 생성자를 가져올 수 있습니다.
Constructor<?>[ ] constructors = clazz.getDeclaredConstructors();

특정 요소의 특정 어노테이션	특정 요소란 클래스면 클래스, 필드면 필드, 메서드면 메서드중 하나에서 직접 선언된 특정 어노테이션을 리턴한다. 상속된 어노테이션은 미포함이다.
MyAnnotation classAnnotation = clazz.getDeclaredAnnotation(MyAnnotation.class);
MyAnnotation methodAnnotation = method.getDeclaredAnnotation(MyAnnotation.class);
여기선 @MyAnnotation 어노테이션이 붙었는지 확인하고 존재하면 해당 어노테이션 객체를, 그렇지 않으면 null을 반환합니다.

참고	확실하다면 Annotation으로 받아서 형변환 시켜줘도 된다
Annotation methodAnno = method.getDeclaredAnnotation(RequestMapping.class);
RequestMapping rm = (RequestMapping) methodAnno;

특정 요소의 모든 어노테이션(상속x)	특정 요소란 클래스면 클래스, 필드면 필드, 메서드면 메서드중 하나에서 직접 선언된 모든 어노테이션을 배열 형태로 가져온다. 상속된 어노테이션은 미포함이다
Annotation[ ] declaredAnnotations =clazz.getDeclaredAnnotations();

클래스의 모든 어노테이션(상속 포함)	getAnnotations() 메서드를 사용하여 클래스에 적용된 모든 어노테이션을 배열 형태로 가져올 수 있습니다.
Annotation[ ] classAnnotations = clazz.getAnnotations();
클래스, 필드, 메서드에 적용된 모든 어노테이션을 반환합니다.


클래스의 패키지	getPackage() 메서드를 사용하여 클래스가 속한 패키지에 대한 정보를 가져올 수 있습니다.
Package pkg = clazz.getPackage();


getClass()로 가져온 메서드 호출

method.invoke(객체)	호출시 객체 인스턴스 전달.  매개변수를 받는다면 두 번째 전달자부터
methodSayHello.invoke(example);  // 인스턴스에 대해 호출
methodGreet.invoke(example, "Alice");  // 인스턴스에 대해 호출, 매개변수 전달

static 메서드일 경우 invoke()	staticMethod.invoke(null);  // 정적 메서드는 인스턴스가 필요 없습니다. 호출 시 null을 전달합니다


invoke() 예외	invoke 메서드는 다양한 예외를 던질 수 있습니다. 주요 예외는 다음과 같습니다:

IllegalAccessException: 접근 권한이 없는 메서드를 호출할 때 발생합니다.
IllegalArgumentException: 메서드에 제공된 인수의 타입이 맞지 않을 때 발생합니다.
InvocationTargetException: 메서드 호출 중 발생한 예외를 래핑합니다.



         */

