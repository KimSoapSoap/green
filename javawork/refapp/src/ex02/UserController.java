package ex02;

public class UserController {

    //   /board/save-form 연결

    @RequestMapping(uri = "/board/save-form")
    public void saveForm() {
        System.out.println("save-form 호출됨");
    }

    @RequestMapping(uri = "/login")
    public void login() {
        System.out.println("login 호출됨");
    }

    @RequestMapping(uri = "/join")
    public void join() {
        System.out.println("join 호출됨");
    }

    @RequestMapping(uri = "/userInfo")
    public void userInfo() {
        System.out.println("userInfo 호출됨");
    }
    
}
