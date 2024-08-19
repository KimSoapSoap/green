package ex01;


//A개발자
public class App {

    public static void main(String[] args) {
        String path = "/userinfo";

        UserController uc = new UserController();

        if(path.equals("/login")) {
            uc.login();
        }else if(path.equals("/join")) {
            uc.join();
        }else if(path.equals("/userinfo")){
            uc.userInfo();
        }
    }
}
