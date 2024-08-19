package ex03_adapter_pattern;

import ex03_adapter_pattern.lib.OuterRabbit;

/*
Animal 추상 클래스와 호환되지 않는 OuterRabbit객체를 호환되게 하기 위해서  RabbitAdapter가 Animal을 상속받고 OuterRabbit을 멤버변수에 넣고 생성자에서 OuterRabbit 객체를 주입했다.
즉 RabbitAdapter에 우리가 필요한 기능인 getName()을 Animal을 상속받아서 넣고 우리가 변경할 수 없는 OuterRabbit을 멤버변수로 생성자에서 주입 받고 어댑터가 감싼 것이다.
멤버변수로 받았으므로 RabbitAdapter가 내부에서 OuterRabbit을 사용해서 getName() {  return rabbit.getFullname(); }  으로 OuterRabbit의 이름을 구할 수 있게 됐다. 호환성 완료

이 경우에 RabbitAdapter가 Animal을 상속받았는데 RabbitAdapter가 Animal인가?
이름이 RabbitAdapter일 뿐이고 Rabbit이라고 봐도 무방하다.

프록시랑 비슷하지만 어댑터는 호환이 안 되는 것을 해결 해주는 것이고

프록시는 할 수 없는 것을 대신하게 하는 것이다.(부가기능)

 */

//~~Adapter라는 클래스를 보면 호환 안 되는 것을 호환되게 해줬구나. 이렇게 파악하면 된다

//어쨌든 ex01~03까지의 핵심은 추상화다.
public class RabbitAdapter extends Animal {
    private OuterRabbit rabbit;

    public RabbitAdapter(OuterRabbit rabbit) {
        this.rabbit = rabbit;
    }

    @Override
    String getName() {
        return rabbit.getFullname();
    }
}
