package ex03_adapter_pattern;

public class Doorman {

    //쥐 출입금지
    public void 쫓아내(Animal m) {
        System.out.println(m.getName() + "쫓아내");
    }
}
