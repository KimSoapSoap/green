package ex02_proxy_pattern;

public class DoormanProxy {  //DoormanProxy는 Doorman이 아니기 때문에 상속사용 X
                            // 멤버 변수에 넣고 생성자 주입을 통해 부가적인 로직을 처리
    private Doorman doorman;

    public DoormanProxy(Doorman doorman) {
        this.doorman = doorman;
    }

    public void 쫓아내(Animal a) {
        System.out.println("안녕~~");
        doorman.쫓아내(a);
        System.out.println("잘가~~");
    }
}
