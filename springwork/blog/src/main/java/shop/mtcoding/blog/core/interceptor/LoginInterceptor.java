package shop.mtcoding.blog.core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.mtcoding.blog.core.error.ex.Exception401;
import shop.mtcoding.blog.user.User;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new Exception401("인증되지 않았습니다.");


            /*
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print("<script> alert('인증되지 않았습니다.');\n");
            pw.print("location.href='/login-form';\n");
            pw.flush(); //전송
            return false;

            //끝에 \n을 붙이는 이유는 한 줄 단위로 parsing을 하는데 이렇게 \n으로 줄바꿈을 하지 않으면 꼬인다.
            //flush()와 \n은 기본기다.
            //PrintWriter는 내부적으로 flush()를 처리해준다.
            //그리고 println()을 하면 줄바꿈도 해주므로 \n을 해주지 않아도 된다.
            //BufferedWriter는 flush 붙여주고 \n 써줘야 한다
            */

        }

        return true;  // false면 컨트롤러 진입 안 됨. 검증하고 넘어왔으므로 true로 해서 컨트롤러에 보낸다.
    }
}
