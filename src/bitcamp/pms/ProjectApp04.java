/* 목표
- 각 명령어(add,update,list,delete,quit,기타)를 처리하는 코드를
  별도의 메서드로 분리하라.
- 사용 문법:
  => 메서드 정의 및 호출 --> 재사용할 수 있는 코드로 만들자!

* Refectoring
- 유지보수가 용이한 구조로 개편하는 것.
예) 주석을 달아야 하는 로직을 메서드로 분리하여 코드 해석을 쉽게 만드는 행위.
   => extract method
   => 이 외에도 100가지 이상의 기법이 있다.

1) prompt() 메서드 정의
2) quit 명령을 처리하는 코드를 메서드로 분리. => doQuit() 정의
3) 기타 명령에 대해 오류를 출력하는 코드를 분리 => doError() 정의
4) add 명령을 처리하는 코드 분리 => doAdd() 정의
5) update 명령을 처리하는 코드 분리 => doUpdate() 정의
*/
package bitcamp.pms;

import java.util.Scanner;
import bitcamp.pms.domain.Member;

public class ProjectApp {
  static Scanner keyScan = new Scanner(System.in);
  static Member[] members = new Member[1000];
  static int count = 0;

  public static void main(String[] args) {

    Member member = null;
    String input;
    int no = 0;

    while (true) {
      input = prompt();

      if (input.equals("quit")) {
        doQuit();
        break;
      } else if (input.equals("add")) {
        doAdd();
      } else if (input.equals("update")) {
        System.out.print("변경할 회원 번호는? ");
        no = Integer.parseInt(keyScan.nextLine());

        member = new Member();

        System.out.printf("이름(%s)? ", members[no].getName());
        member.setName(keyScan.nextLine());

        System.out.printf("이메일(%s)? ", members[no].getEmail());
        member.setEmail(keyScan.nextLine());

        System.out.printf("암호(%s)? ", members[no].getPassword());
        member.setPassword(keyScan.nextLine());

        System.out.printf("전화(%s)? ", members[no].getTel());
        member.setTel(keyScan.nextLine());

        if (confirm("변경하시겠습니까?", true)) {
          members[no] = member;
          System.out.println("변경하였습니다.");
        } else {
          System.out.println("변경을 취소하였습니다.");
        }
      } else if (input.equals("list")) {
        for (int i = 0; i < count; i++) {
          System.out.printf("%d, %s\n", i,
            (members[i] != null)?members[i].toString():"");
        }

      } else if (input.equals("delete")) {
        System.out.print("삭제할 회원 번호는? ");
        no = Integer.parseInt(keyScan.nextLine());
        if (confirm("정말 삭제하시겠습니까?", true)) {
          members[no] = null;
          for (int i = no + 1; i < count; i++) {
            members[i-1] = members[i];
          }
          count--;
          System.out.println("삭제하였습니다.");
        } else {
          System.out.println("삭제를 취소하였습니다.");
        }

      } else {
        doError();
      }
    }
  }

  static boolean confirm(String message, boolean strictMode) {
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

  static String prompt() {
    System.out.print("명령> ");
    return keyScan.nextLine().toLowerCase();
  }

  static void doQuit() {
    System.out.println("안녕히 가세요!");
  }

  static void doError() {
    System.out.println("올바르지 않은 명령어입니다.");
  }

  static void doAdd() {
    Member member = new Member();

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
  }
}
