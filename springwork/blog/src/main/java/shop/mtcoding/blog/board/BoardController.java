package shop.mtcoding.blog.board;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.core.Hello;
import shop.mtcoding.blog.user.User;

import java.util.List;

// 식별자 요청 받기, 응답하기 (이 클래스의 책임)
@RequiredArgsConstructor
@Controller //식별자 요청을 받을 수 있다.
public class BoardController {


    private final BoardService boardService;
    private final HttpSession session; //스프링이 서버 시작시에 편의성을 위해 HttpSession을 생성해서 IoC 컨테이너에 올려놨기 때문에 주입 가능


    // url : https://localhost:8080/board/1/update
    // body : title = 제목1변경&content=내용1변경
    // conent-type : x-www-form-urlencoded
    @Hello
    @PostMapping("/api/board/{id}/update")
    public String update(@PathVariable("id") int id, @Valid BoardRequest.UpdateDTO updateDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
      /*  if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }*/
        boardService.게시글수정(id, updateDTO, sessionUser);
        return "redirect:/board/" + id;

    }


    @PostMapping("/api/board/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        //원래는 검증을 하고 지워야 되지만 V1에서는 그냥 바로 삭제한다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        //로그인 체크정도는 컨트롤러에서 검증을 해준다.
        //이걸 서비스 넘어가서 권한체크를 하면 불필요한 트랜잭션이 걸린다
    /*    if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }*/
        boardService.게시글삭제(id, sessionUser);
        return "redirect:/";
    }

    // 매핑 주소로  /board/save 에서 save를 붙인 것은 HTTP 1.0으로 만들었기 때문입니다.
    // 1.0에는 GET, POST밖에 없기 때문에 POST로 insert, update, delete를 모두 만들었기 때문에 save를 붙여서 구별해줬습니다.
    // 1.1에는 PUT, DELETE가 나와서 이때는 같은 주소로 POST PUT DELETE 로  insert update delete
    @PostMapping("/api/board/save")
    //@Valid가 붙어 있으면 DTO가 만들어 질 때 어노테이션을 분석해서 Null이거나 공백이면 Error error로 객체를 넘겨준다.
    public String save(@Valid BoardRequest.SaveDTO saveDTO, Errors errors) {  //스프링 기본전략 = x-www-form-urlencoded 파싱
        //SaveDTO를 받을 수 있는 것은 우리는 글작성 페이지에서 title과 content 정보를 POST로 보내는데
        // @ModelAttribute가 이 전달되는 title과 content를 필드로 가지고 있는 객체가 있다면 해당 객체를 매개변수로 설정할 때
        // 자동으로 매핑 해준다. 이 SaveDTO는 우리가 title과 content를 멤버변수로 만든 객체이므로 가능

//        System.out.println(title);
//        System.out.println(content);
//        boardRepository.save(title, content);

        User sessionUser = (User) session.getAttribute("sessionUser");

        // session 유저 꺼내서 검증 필요함. 통과하면 저장
        // 모든 검증을 하나로 모아서 할 것이기 때문에 if-else로 오류페이지로 보내고 하지 말자
  /*      if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }*/

        boardService.게시글쓰기(saveDTO, sessionUser);
        return "redirect:/";
    }

    // get, post, put, delete -> 일단 get과 post만 씀. get은 select,  post는 insert, update, delete
    @GetMapping("/")   // 인터셉터 추가 후 메인페이지를 /board -> /로 변경했다.
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

        List<Board> boardList = boardService.게시글목록보기();
        request.setAttribute("models", boardList);

        return "board/list";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/1
    // 3. 응답 : board/detail
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        // Board board = boardRepository.findById(id);
        //inner join하고 와서 user객체 정보도 가지고 있으므로 user에 접근하려면 model.user로 접근하면 된다
        // request.setAttribute("model", board);
        //boolean 값을 하나 만들었다. -> 게시글 상세보기에서 권한확인을 위해 수정/삭제버튼을 이 값을 일단 넣어둠
        //request.setAttribute("isOwner", false);

        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTOV2 detailDTOV2 = boardService.상세보기(id, sessionUser);
        request.setAttribute("model", detailDTOV2);

        return "board/detail";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/save-form
    // 3. 응답 : board/save-form
    @GetMapping("/api/board/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
     /*   if (sessionUser == null) {
            throw new Exception401("인증되지 않았습니다.");
        }*/

        return "board/save-Form";
    }

    // 1. 메서드 : Get
    // 2. 주소 : /board/1/update-form
    // 3. 응답 : board/update-form

    //{id}는 정규표현식인데 @PathVariable이 이를 받아준다.

    @GetMapping("/api/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
    /*    if (sessionUser == null) {
            throw new Exception401("인증되지 않았습니다.");
        }*/

        Board board = boardService.게시글수정화면가기(id, sessionUser);
        request.setAttribute("model", board);
        return "board/update-form";
    }


    //  주소 사용시 주의점	주소에는 언더바( _ )를 사용하지 않고 하이픈 ( - ) 을 사용한다.
    //	주소에는 대문자를 사용하지 않는다.
    //	자바 메서드 이름은 camelCase를 사용한다
    //	일종의 개발자들 간 컨벤션(Convention)으로 약속이다


    //LAZY 테스트
    //application.properties에서
    //spring.jpa.open-in-view=false   옵션을 true로 하면 LAZY로딩 발생, false로 하면 LAZY로딩 발생하지 않음
    //false면 request가 응답 나가기 전 Controller 에서는 Connection(DB접근 권한)을 가지고 있지 않다.(Service -> Controller 갈 때 Connection 끊어줌
    //true면 request가 응답 나가기 전 Controller에서도 Connection을 가지고 있기 때문에 DB에 접근 가능하다. 이때 return시 불필요한 조회가 일어날 수 있기 때문에
    //OIV를 false로 해준다.
    //아래 예시에서는 true로 하면 Board게시판 조회 findAll()을 했는데 (우리는 게시판 목록에 글쓴이 정보가 없다는 것을 상기해야 한다) 불필요한 User를 조회해버린다
    //false로 하면 나가갈 때 Service가 Connection을 끊어줘서 나갈 때 request는 Controller에서 Connection이 없기 때문에 Controller에서 불필요한 조회를 차단
    // LazyInitializationException 발생
    @GetMapping("/test/board/1")
    public void testBoard() {
        List<Board> boardList = boardService.게시글목록보기();
        System.out.println("-------------------start----------------------");
        System.out.println(boardList.get(2).getUser().getPassword());
        System.out.println("--------------------end----------------------");
    }

}
