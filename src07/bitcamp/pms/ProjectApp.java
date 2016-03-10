/* 목표
- 회원 정보 입력 회수를 다섯 개로 고정하지 않는다.
- 사용자에게 계속 입력할 지 물어서 결정한다.
이름? 홍길동
이메일? hong@test.com
암호? 1111
전화? 111-1111
저장하시겠습니까?(y/n) y
저장하였습니다.
계속입력하시겠습니까?(y/n) y
....
계속입력하시겠습니까?(y/n) n
------------------------------
(지금까지 입력한 내용을 출력한다.)
- 사용 문법
  => 레퍼런스 배열과 입력 개수를 센다.
*/
package bitcamp.pms;

import java.util.Scanner;
import bitcamp.pms.domain.Member;

public class ProjectApp {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    Member[] members = new Member[1000];
    int count = 0;

    Member member = null;
    String input = null;

    while (count < 1000) {
      member = new Member();

      System.out.print("이름? ");
      member.setName(keyScan.nextLine());

      System.out.print("이메일? ");
      member.setEmail(keyScan.nextLine());

      System.out.print("암호? ");
      member.setPassword(keyScan.nextLine());

      System.out.print("전화? ");
      member.setTel(keyScan.nextLine());

      do {
        System.out.print("저장하시겠습니까?(y/n) ");
        input = keyScan.nextLine().toLowerCase();
        if (input.equals("y")) {
          members[count++] = member;
          System.out.println("저장하였습니다.");
          break;
        } else if (input.equals("n")) {
          System.out.println("저장을 취소하였습니다.");
          break;
        } else {
          System.out.println("잘못된 명령어입니다.");
        }
      } while(true);

      System.out.print("계속하시겠습니까?(y/n) ");
      input = keyScan.nextLine();
      if (!"y".equalsIgnoreCase(input)) {
        break;
      }
    }

    System.out.println("--------------------------------");

    for (int i = 0; i < count; i++) {
      System.out.println(members[i]);
    }
  }
}
