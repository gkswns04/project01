package bitcamp.pms.controller;

import java.util.Scanner;
import java.util.ArrayList;
import bitcamp.pms.domain.Member;

public class MemberController {
  private Scanner keyScan;
  private ArrayList<Member> members;

  public MemberController() {
    members = new ArrayList<>();
  }

  public void setScanner(Scanner keyScan) {
    this.keyScan = keyScan;
  }

  public void service() {
    String input = null;
    do {
      input = prompt();
      try {
        switch (input) {
          case "add": doAdd(); break;
          case "list": doList(); break;
          case "update": doUpdate(); break;
          case "delete": doDelete(); break;
          case "main": break;
          default:
            System.out.println("지원하지 않는 명령어입니다.");
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println("유효하지 않은 인덱스입니다.");
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
      members.add(member);
      System.out.println("저장하였습니다.");
    } else {
      System.out.println("저장을 취소하였습니다.");
    }
  }

  private void doUpdate() {
    System.out.print("변경할 회원 번호는? ");
    int no = Integer.parseInt(keyScan.nextLine());

    Member oldMember = members.get(no);
    Member member = new Member();

    System.out.printf("이름(%s)? ", oldMember.getName());
    member.setName(keyScan.nextLine());

    System.out.printf("이메일(%s)? ", oldMember.getEmail());
    member.setEmail(keyScan.nextLine());

    System.out.printf("암호(%s)? ", oldMember.getPassword());
    member.setPassword(keyScan.nextLine());

    System.out.printf("전화(%s)? ", oldMember.getTel());
    member.setTel(keyScan.nextLine());

    if (confirm("변경하시겠습니까?", true)) {
      members.set(no, member);
      System.out.println("변경하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  private void doList() {
    Member member = null;
    for (int i = 0; i < members.size(); i++) {
      member = members.get(i);
      System.out.printf("%d, %s\n", i, member.toString());
    }
  }

  private void doDelete() {
    System.out.print("삭제할 회원 번호는? ");
    int no = Integer.parseInt(keyScan.nextLine());

    if (confirm("정말 삭제하시겠습니까?", true)) {
      members.remove(no);
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
