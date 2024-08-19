package ex05_template_method_pattern.v2.teacher;

public class PythonTeacher {
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

    public void 강의하기() {
        System.out.println("파이썬 강의하기");
    }

    public void 퇴장하기() {
        System.out.println("퇴장하기");
    }

}
