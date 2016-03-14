/* 멤버 관리 명령 처리 추가
1) service() 메서드 추가
  => "회원관리에 오신 걸 환영합니다." 메시디 출력

2) service()에서 회원 관리 명령 처리
"회원관리>" 프롬프트를 출력하고,
사용자로부터 add, list, update, delete 명령어를 입력받아 출력한다.
만약 "main" 명령이 들어오면 메소드 실행을 종료한다.
---------------------------
회원관리> add
(예전과 같이 처리)
회원관리> list
(예전과 같이 처리)
회원관리> update
(예전과 같이 처리)
회원관리> delete
(예전과 같이 처리)
회원관리> main
(service() 실행 종료)
---------------------------

*/
package bitcamp.pms.controller;

import java.util.Scanner;
import bitcamp.pms.domain.Member;

public class MemberController {
  private Scanner keyScan;
  private Member[] members = new Member[1000];
  private int count = 0;

  public void setScanner(Scanner keyScan) {
    this.keyScan = keyScan;
  }

  public void service() {
    String input = null;
    do {
      input = prompt();
      switch (input) {
        case "add": doAdd(); break;
        case "list": doList(); break;
        case "update": doUpdate(); break;
        case "delete": doDelete(); break;
        case "main": break;
        default:
          System.out.println("지원하지 않는 명령어입니다.");
      }
    } while (!input.equals("main"));
  }

  private String prompt() {
    System.out.print("회원관리> ");
    return keyScan.nextLine().toLowerCase();
  }

  private void doAdd() {
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

  private void doUpdate() {
    System.out.print("변경할 회원 번호는? ");
    int no = Integer.parseInt(keyScan.nextLine());

    Member member = new Member();

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
  }

  private void doList() {
    for (int i = 0; i < count; i++) {
      System.out.printf("%d, %s\n", i,
        (members[i] != null) ? members[i].toString() : "");
    }
  }

  private void doDelete() {
    System.out.print("삭제할 회원 번호는? ");
    int no = Integer.parseInt(keyScan.nextLine());

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
  }

  private boolean confirm(String message, boolean strictMode) {
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
