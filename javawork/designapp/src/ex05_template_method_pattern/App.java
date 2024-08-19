package ex05_template_method_pattern;

import ex05_template_method_pattern.teacher.HtmlTeacher;
import ex05_template_method_pattern.teacher.JavaTeacher;
import ex05_template_method_pattern.teacher.PythonTeacher;
import ex05_template_method_pattern.teacher.SpringTeacher;

/**  /** 하고 엔터치면 이런 형태가 나온다. -참고-
 *
 *      템플릿 : 반복적인 작업을 간편하게 하기 위해 미리 정의된 틀이나 형식
 *
 */

public class App {
    public static void main(String[] args) {
        JavaTeacher jt = new JavaTeacher();
        jt.수업하기();

        PythonTeacher pt = new PythonTeacher();
        pt.수업하기();

        HtmlTeacher ht = new HtmlTeacher();
        ht.수업하기();

        SpringTeacher st = new SpringTeacher();
        st.수업하기();

    }
}
