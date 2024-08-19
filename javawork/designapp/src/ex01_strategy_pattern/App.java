package ex01_strategy_pattern;

/*
    Animal 생성(abstract)
    타입 일치(다형성) = 쥐(동물), 호랑이(동물)
    문지기한테 DIP 지켜주면 됨

 */

public class App {
    public static void main(String[] args) {
        Doorman d1 = new Doorman();
        Tiger t1 = new Tiger();
        Mouse m1 = new Mouse();
        d1.쫓아내(m1);
    }
}
