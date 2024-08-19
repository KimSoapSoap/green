package ex00;

//객체지향의 핵심

//static이 아니기 때문에 인스턴스가 생성될 때 heap영역에 존재하게 된다.
//static이 붙어 있는 것이 아니라면 new A() 될 때 멤버변수와(값 포함) 메서드(이름만)는 heap 영역에 올라간다
//static메서드와 그냥 메서드가 실행될 때 메서드 내부의 지역변수는 메서드가 실행될 때 stack 영역에
//올라 갔다가 메서드가 종료될 때 사라진다.
//static 메서드는 사용될 준비만 먼저 된 것이고 메서드 사용될 때 지역변수는 stack영역에서 사용됐다가 사라짐

class Alice {
    private int thirst;     // private으로 데이터를 담는 멤버변수를 외부에서 접근 방지.
                            // new Alice() 할 때 heap에 뜸

    public Alice(int thirst) {  //객체 지향에서 초기화는 생성자에서 해준다.
        this.thirst = thirst;
    }

    void drink() {   //new Alice() 할 때 heap에 뜸
        thirst = 0;
    }

    public int getThirst() {  // new Alice() 할 때 heap에 뜸(메서드이므로 이름만 뜸)
        return thirst;
    }

    //Setter인데 setThirst는 좀 이상하다. drink()가 훨씬 직관적이다.
    //그렇기 때문에 getter는 IDE로 자동으로 만들어도 setter는 직접 만드는 것이 좋다.
    int checkThirst() {
        return thirst;
    }

}

public class Mem01 {
    public static void main(String[] args) {
        Alice e = new Alice(100);   //heap영역에 Alice의 공간이 생긴다.
                                          //e는 이 new Alice()를 가리키며 main stack이 관리한다.
                                          //만약 e1으로 또 new Alice() 해주면 새로운 e1이라는 Alice가 heap 영역에 생성된다
                                          //생성시 thirst 값 100으로 초기화

        //e.thirst = 100; //상태는 행위로 변경해야되므로 객체 지향이 아니다. 메서드로 변경해야됨

        //값 변경 (행위). 객체지향의 핵심이다. 상태(멤버변수)는 행위(메서드)로 변경해준다.
        e.drink();

        //값 확인
        int thirst = e.getThirst();
        System.out.println(thirst);
    }
}
