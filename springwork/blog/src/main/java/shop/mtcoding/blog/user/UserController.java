package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //HttpSession은 스프링 시작시에 Ioc에(빈 컨테이너) 생성돼서 대기중이다. @Autowired로 주입
    @Autowired
    private HttpSession session;

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/board";
    }


    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userRepository.save(joinDTO.toEntity());
        return "redirect:/login-form";
    }


    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }
}
