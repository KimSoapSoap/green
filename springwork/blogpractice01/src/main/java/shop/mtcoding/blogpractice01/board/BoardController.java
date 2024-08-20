package shop.mtcoding.blogpractice01.board;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id) {
        boardRepository.delete(id);
        return "redirect:/board";

    }



    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id, String title, String content) {
        boardRepository.update(id, title, content);
        return "redirect:/board/" + id;
    }


    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable int id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("model", board);
        return "board/update-form";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("model", board);
        return "/board/detail";

    }


    @PostMapping("/board/save")
    public String save(String title, String content) {
        boardRepository.save(title, content);
        return "redirect:/board";
    }


    @GetMapping("/board")
    public String board(HttpServletRequest request) {
        List<Board> list = boardRepository.findAll();
        request.setAttribute("models", list);
        return "/board/list";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "/board/save-form";
    }

}
