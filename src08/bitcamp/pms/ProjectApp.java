/* 목표
- 사용자로부터 저장 여부 또는 계속 입력 여부를 묻는 코드를
  다음의 메서드로 분리하라.
  => static boolean confirm(String message) {}
  => message : 프롬프트에 출력하는 문자열
  예) confirm("저장하시겠습니까?");
      저장하시겠습니까?(y/n)
  예) confirm("계속하시겠습니까?");
      계속하시겠습니까?(y/n)
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


      if (confirm("저장하시겠습니까?", true)) {
        members[count++] = member;
        System.out.println("저장하였습니다.");
      } else {
        System.out.println("저장을 취소하였습니다.");
      }

      if (!confirm("계속하시겠습니까?", false)) {
        break;
      }
    }

    System.out.println("--------------------------------");

    for (int i = 0; i < count; i++) {
      System.out.println(members[i]);
    }
  }

  static boolean confirm(String message, boolean strictMode) {
    Scanner keyScan = new Scanner(System.in);
    String input = null;
    do {
      System.out.printf("%s(y/n) ", message);
      input = keyScan.nextLine().toLowerCase();

      if (input.equals("y")) {
        return true;
      } else if (input.equals("n")) {
        return false;
      } else {
        if (!strictMode) {
          return false;
        }
        System.out.println("잘못된 명령어입니다.");
      }
    } while(true);
  }
}
