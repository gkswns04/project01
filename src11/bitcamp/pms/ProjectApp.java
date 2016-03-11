/* 목표
- 멤버 정보를 다루는 메서드를 별도의 클래스로 분류한다.
- 사용 문법:
  => 클래스 정의
  => 인스턴스 필드와 인스턴스 멤버

1) bitcamp.pms.controller 패키지를 만든다.
2) 회원 정보와 관련된 메서드만 추출하여 별도의 클래스로 분류한다.
  => MemberController 클래스 생성
3) MemberController을 필드를 인스턴스로 전환한다.
  => 그 필드를 사용하는 메서드도 인스턴스 메서드로 전환한다.

*/
package bitcamp.pms;

import java.util.Scanner;
import bitcamp.pms.controller.MemberController;

public class ProjectApp {
  static Scanner keyScan = new Scanner(System.in);

  public static void main(String[] args) {
    // 멤버 관리 객체 생성
    MemberController memberController = new MemberController();
    memberController.setScanner(keyScan); // <-- 의존 객체 주입

    String input;

    while (true) {
      input = prompt();

      if (input.equals("quit")) {
        doQuit();
        break;
      } else if (input.equals("add")) {
        memberController.doAdd();
      } else if (input.equals("update")) {
        memberController.doUpdate();
      } else if (input.equals("list")) {
        memberController.doList();
      } else if (input.equals("delete")) {
        memberController.doDelete();
      } else if (input.equals("about")) {
        doAbout();
      } else {
        doError();
      }
    }
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

  static void doAbout() {
    System.out.println("비트캠프 80기 프로젝트 관리 시스템!");
  }
}
