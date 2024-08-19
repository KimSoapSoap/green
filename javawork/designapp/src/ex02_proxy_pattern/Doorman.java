package ex02_proxy_pattern;

public class Doorman {

    //쥐 출입금지
    public void 쫓아내(Animal m) { // OCP
        System.out.println("안녕");
        System.out.println(m.getName() + "쫓아내");
    }
}
