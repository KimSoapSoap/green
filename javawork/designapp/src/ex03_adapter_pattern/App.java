package ex03_adapter_pattern;

/*
    Animal 생성(abstract)
    타입 일치(다형성) = 쥐(동물), 호랑이(동물)
    문지기한테 DIP 지켜주면 됨

 */

import ex03_adapter_pattern.lib.OuterRabbit;

public class App {
    public static void main(String[] args) {
        Doorman d1 = new Doorman();
        Tiger t1 = new Tiger();
        Mouse m1 = new Mouse();
        d1.쫓아내(m1);

        RabbitAdapter rabbit = new RabbitAdapter(new OuterRabbit());
        d1.쫓아내(rabbit);
    }
}
