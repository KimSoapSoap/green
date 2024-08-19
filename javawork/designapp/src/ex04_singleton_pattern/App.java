package ex04_singleton_pattern;

/*
    Animal 생성(abstract)
    타입 일치(다형성) = 쥐(동물), 호랑이(동물)
    문지기한테 DIP 지켜주면 됨

 */

public class App {
    public static void main(String[] args) {
        /*
        Doorman d1 = new Doorman();
        Doorman d2 = new Doorman(); // Doorman을 new 두 번 하는 거 막기(싱글톤 패턴).

        */
        /*  방법1
        Doorman d1 = Doorman.getInstance();
        Doorman d2 = Doorman.getInstance();
        */

        // 방법2  - 우린 이 방법을 사용한다.
        Doorman d1 = Doorman.instance;
        Doorman d2 = Doorman.instance;

        System.out.println(d1.hashCode());
        System.out.println(d2.hashCode());
    }
}
