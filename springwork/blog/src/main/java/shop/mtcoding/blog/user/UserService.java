package shop.mtcoding.blog.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception400;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        User oldUser = userRepository.findByUsername(joinDTO.getUsername());

        //비밀번호를 암호화 해서 넣는 로직을 서비스에서 넣어준다.

        //if - else를 가능하면 쓰지 말고 걸러낼 것만 if로 필터링 하듯이 걸러내면 다 걸러내고 마지막에 정상처리 해주면 된다.
        if (oldUser != null) {
            throw new Exception400("이미 존재하는 유저네임입니다.");
        }

        userRepository.save(joinDTO.toEntity());
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        //비밀번호를 암호화 해놨기 때문에 로그인시 입력한 비밀번호와 암호화 해둔 코드값과 비교하는 로직 필요
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        return user;
    }

}
