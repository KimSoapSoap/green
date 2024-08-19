package ex00;


abstract class Car { //추상화를 위한 클래스. abstract로 추상클래스로 만든다.
                    //new Car을 할 수 없게 된다. 추상화된 개념이므로 객체생성할 필요가 없다.
    // car 상태
    // car 행위
    abstract void run();  //추상 메서드. 오버라이딩을 강제
}



class Sonata extends Car { //다형성
    // Sonata 상태
    // Sonata 행위
    void run() { //재정의
        System.out.println("소나타 달린다 ㄱㄱ");
    }
}

class Genesis extends Car { //다형성
    // Genesis 상태
    // Genesis 행위
    void run() { //재정의
        System.out.println("제네시스 ㄱㄱ");
    }
}

public class Mem02 {

    
}
