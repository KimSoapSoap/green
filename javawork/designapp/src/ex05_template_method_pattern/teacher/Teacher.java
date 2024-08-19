package ex05_template_method_pattern.teacher;

//Teacher라는 추상 메서드를 만들어서 변하지 않는 공통적인 것을 모아서 끌어 올렸다.
//변하는 것은 abstract 메서드로 남겨놓고 하위 클래스에서 로직을 처리
public abstract class Teacher {
    public void 수업하기() {
        입장하기();
        출석부르기();
        강의하기();
        퇴장하기();
    }

    public void 입장하기() {
        System.out.println("입장하기");
    }

    public void 출석부르기() {
        System.out.println("출석부르기");
    }

    public abstract void 강의하기();

    public void 퇴장하기() {
        System.out.println("퇴장하기");
    }
}
