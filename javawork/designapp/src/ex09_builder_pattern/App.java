package ex09_builder_pattern;

public class App {
    public static void main(String[] args) {
        //Person p1 = new Person();    // Person의 기본 생성자를 private으로 막아놔서 생성 불가능
        //Person p1 = new Person(1, "홍길동", 20, "dong@nate.com");    //
        //Person p1 = Person.builder().id(1);  //id()의 리턴 타입이 Person이라서 가능. void면 불가능

        //생성자는 몇번 째에 무엇을 넣어야 되는지 순서를 알아야 한다.
        //생성자는 또한 무언가를 넣고 싶지 않다면 해당 필드(멤버변수)를 제외한 생성자를 새로 만들어야 한다.
        //즉 생성자 오버로딩 해야한다.

        //빌더는 순서가 상관이 없고 무엇을 넣어야 하는지 메서드 이름을 통해 명확하게 알 수 있다.
        //넣고 싶지 않은 필드는 값을 안 넣어줘도 된다.
        //여기서 주의할 점은 값을 넣지 않으면 null로 표시해야되는데 기본형은 null값이 없다.
        //그렇기 때문에 Builder로 사용할 때 기본형 변수는 래퍼 클래스로 만들자. ex) int -> Integer
        Person p1 = Person.builder().id(1).name("홍길동").age(20).email("dong@nate.com");



        //이름과 이메일만 넣고 싶다.
        Person p2 = Person.builder().name("홍길동").email("dong@nate.com");
        System.out.println(p2.getAge());

    }
}
