package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 식별자 요청 받기, 응답하기 (이 클래스의 책임)
@Controller //식별자 요청을 받을 수 있다.
public class BoardController {

    @Autowired
    private final BoardRepository boardRepository;


    public BoardController(BoardRepository boardRepository) {
        //System.out.println("Controller 생성자");
        this.boardRepository = boardRepository;
    }


    // url : https://localhost:8080/board/1/update
    // body : title = 제목1변경&content=내용1변경
    // conent-type : x-www-form-urlencoded
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") int id, String title, String content) {
        boardRepository.updatdById(title, content, id);
        return "redirect:/board/" + id;

    }


    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        //원래는 검증을 하고 지워야 되지만 V1에서는 그냥 바로 삭제한다.
        boardRepository.deleteById(id);
        return "redirect:/board";
    }


    @PostMapping("/board/save")
    public String save(String title, String content) {  //스프링 기본전략 = x-www-form-urlencoded 파싱

        System.out.println(title);
        System.out.println(content);
        boardRepository.save(title, content);
        return "redirect:/board";
    }

    // get, post, put, delete -> 일단 get과 post만 씀. get은 select,  post는 insert, update, delete
    @GetMapping("/board")
    //매개변수에 HttpServletRequest request를 넣으면 필요한가보다 하고 DispatcherServlet이 리플렉션으로 주입해준다.
    //만약 Board board로 넣어주면 null이 뜬다.  DispatcherServlet은 request와 response만 들고 있기 때문
    //HttpSession session을 넣는다고 하면 DispatcherServlet이 HttpSession은 가지고 있지 않지만
    //가지고 있는 request로 session에 접근하고 가져와서 주입해주기 때문에 HttpSession session을 매개변수로 넣을 수 있고
    //이 HttpSession은 Ioc(빈 컨테이너)에서 싱글톤으로 관리되고 있기 때문에 멤버변수에 넣고 @Autowired 해줄 수 있다.
    //반면에 HttpServletRequest와 HttpServletResponse는 각각의 요청마다 존재해야 하기 때문에 멤버변수에서 @Autowired 불가능
    //Model model은 데이터 전달 객체. 매개변수에 Model model을 받아서 model객체에 넣은 데이터는 request 객체로 옮겨진다.
    //기존에는 컨트롤러에서 받은 model 객체를 이용해서 정보를 전달했다.
    //model.addAttribute("name", userName) 이렇게 view의 request에 "name"으로 userName값 전달을 했고 이게 Spring MVC권장 방식이다.
    //mastache에서는 model을 사용하지 않고 그냥 request.setAttribute("name" userName) 이렇게 request에 넣어서 view에 전달
    //jsp나 timeleaf(확인 해봐야됨)는 컨트롤러에서 model로 addAttribute 전달
    //참고로 redirect 할 때는 응답이 나가고 GET요청을 받아서 다시 응답을 해주기 때문에 request의 정보가 사라진다.
    //request는 응답이 나가기 전까지만 정보가 유지되기 때문인데 이때 정보를 유지해야 된다면 session을 사용한다.
    public String list(HttpServletRequest request) {  // Controller에서 return하면 검색하는 기본 파일 경로는 templates 디렉토리다

        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("models", boardList);
        return "board/list";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/1
    // 3. 응답 : board/detail
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("model", board);
        return "board/detail";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/save-form
    // 3. 응답 : board/save-form
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-Form";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/1/update-form
    // 3. 응답 : board/update-form

    //{id}는 정규표현식인데 @PathVariable이 이를 받아준다.
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("model", board);
        return "board/update-form";
    }


    //  주소 사용시 주의점	주소에는 언더바( _ )를 사용하지 않고 하이픈 ( - ) 을 사용한다.
    //	주소에는 대문자를 사용하지 않는다.
    //	자바 메서드 이름은 camelCase를 사용한다
    //	일종의 개발자들 간 컨벤션(Convention)으로 약속이다


}
