package shop.mtcoding.springv3.core.util;


//각종 에러메시지 호출과 동시에 뒤로가기 혹은 특정 페이지로 보내는 메서드 만들어 놓고 사용
public class Script {

    //에러 메시지를 받고 에러 메시지 출력 후 뒤로가기
    public static String back(String msg) {
        String errMsg = """                
                <script>
                    alert('$msg'); 
                    history.back();
                </script>
                """.replace("$msg", msg);
        return errMsg;
    }

    //에러 메시지와 보낼 주소 전달 받고 에러 메시지 출력 후 특정 페이지로 이동 시키기
    public static String href(String msg, String url) {
        String errMsg = """                
                <script>
                    alert('$msg'); 
                    location.href = '$url';
                </script>
                """.replace("$msg", msg)
                .replace("$url", url);
        return errMsg;
    }

}
