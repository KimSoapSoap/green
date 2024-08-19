package ex09_builder_pattern;

public class Person {
    private Integer id;
    private String name;
    private Integer age;
    private String email;

    private Person() {}

    public static Person builder() {
        return new Person();
    }

    public Person id(Integer id) {  //만약 리턴 타입이 void라면 그냥 setter다.
        this.id = id;
        return this;    //heap 영역에서 자기 자신의 객체를 this라고 지칭한다.
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public Person age(Integer age) {
        this.age = age;
        return this;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
