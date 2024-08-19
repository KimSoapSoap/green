package ex02_proxy_pattern;

/*
    Animal 생성(abstract)
    타입 일치(다형성) = 쥐(동물), 호랑이(동물)
    문지기한테 DIP 지켜주면 됨

 */

public class App {
    public static void main(String[] args) {
        Mouse m =new Mouse();   //mouse, animal -> Mouse 타입을 바라봄.
                                //쫓아내(m)에서 Aniaml타입으로 들어간 것.
        DoormanProxy doormanProxy = new DoormanProxy(new Doorman());
        doormanProxy.쫓아내(m);
    }
}
