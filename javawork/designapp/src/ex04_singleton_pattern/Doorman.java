package ex04_singleton_pattern;

public class Doorman {

    /*
    // 방법1
    //main 시작전에 Doorman이 생성돼서 doorman 변수에 저장된다.
    private static Doorman doorman = new Doorman();

    //static이 아니면 사용할 방법이 없다. static이 아니면 메서드를 사용하기 위해
    //객체를 생성해야 메서드가 heap영역에 사용할 준비가 완료되는데
    //이 객체는 프로그램이 실행될 때 한 번만 생성되고 이후 객체 생성은
    //private으로 막혀 있기 때문이다. doorman.getInstance()가 불가능하기 때문에
    //getInstance()도 static으로 만들어 준다.
    
    public static Doorman getInstance() {
        return doorman;
    }

    private Doorman() {}

    */

    // 방법2  - 우린 이 방법을 사용한다.
    //public으로 해주기 때문에 변수명을 doorman이라 하지 않고 보통 instance라고 한다.
    //싱글톤은 객체를 단 한 번만 만들고 그걸 사용하는 것이 핵심이므로
    //생성자를 private으로 막아놓고 static 객체를 단 한 번만 만들어놓고 이렇게도 사용한다.
    //외부에서는 Doorman.instance 로 사용하면 된다
    public static Doorman instance = new Doorman();

    private Doorman() {}

    //쥐 출입금지
    public void 쫓아내(Animal m) {
        System.out.println(m.getName() + "쫓아내");
    }
}
