package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.core.Hello;

@RequiredArgsConstructor  // final이 붙은 멤버 변수들을 생성자 주입 해준다.
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;    //HttpSession은 스프링 시작시에 Ioc에(빈 컨테이너) 생성돼서 대기중이다. @Autowired로 주입


    @GetMapping("/logout")
    public String logout() {

        //세션ID값을 가지고 쿠키를 찾아서 쿠키를 날려 버린다.
        session.invalidate();
        return "redirect:/";
    }


    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO loginDTO, Errors errors) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }


    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }


    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @Hello
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }
}
