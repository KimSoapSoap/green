package ex03_adapter_pattern.lib;


//남들이 만든 외부호랑이.
//내가 만들면 extends 하면 되는데 외부에서 만들어진 것이라고 가정.
//이것을 건들지 않고 우리가 사용할 수 있게끔 해보자.

public class OuterRabbit {
    private String fullname = "토끼";

    public String getFullname() {
        return fullname;
    }
}
