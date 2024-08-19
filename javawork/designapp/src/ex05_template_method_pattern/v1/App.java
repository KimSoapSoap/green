package ex05_template_method_pattern.v1;

/**  /** 하고 엔터치면 이런 형태가 나온다. -참고-
 *
 *      템플릿 : 반복적인 작업을 간편하게 하기 위해 미리 정의된 틀이나 형식
 *
 */

public class App {
    public static void main(String[] args) {
        JavaTeacher jt = new JavaTeacher();
        jt.입장하기();
        jt.출석부르기();
        jt.자바강의하기();
        jt.퇴장하기();

        PythonTeacher pt = new PythonTeacher();
        pt.입장하기();
        pt.출석부르기();
        pt.파이썬강의하기();
        pt.퇴장하기();


    }
}
