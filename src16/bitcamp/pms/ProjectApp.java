/* 목표
- 프로젝트 정보를 등록, 목록조회, 변경, 삭제하는 기능을 추가한다.
- 사용 문법:
  => 클래스 정의
  => 여러 클래스의 인스턴스를 다루는 방법

1) 멤버관리와 프로젝트 관리를 선택할 수 있는 메뉴 기능을 추가한다.
- 기존에 동작하던 add, list, update, delete 명령을 막아라!
명령> go member
회원관리>      <--- 프롬프트 아니다. 그냥 출력하라.
명령> go project
프로젝트관리>    <--- 프롬프트 아니다. 그냥 출력하라.
2) go 명령어의 옵션을 처리하는 기능을 추가한다.
3) 명령어 처리 부분을 별도의 메서드로 분리한다.
  => processCommand() 메서드 추가
4) member 관련 명령 처리는 MemberController에게 맡긴다.
  => MemberController에 명령어 처리 기능을 추가한다.
     -> service() 메서드 추가
5) 프로젝트 정보를 다루는 클래스를 추가한다.
  => bitcamp.pms.domain.Project 클래스 추가
     - title:String
     - startDate:java.sql.Date
     - endDate:java.sql.Date
     - description:String
     - state:int
  => bitcamp.pms.controller.ProjectController 클래스 추가
  => "go project" 명령을 처리할 수 있도록 ProjectApp 클래스를 변경
6) 프로젝트의 작업 정보를 다루는 클래스를 추가한다.
  => bitcamp.pms.domain.Task 클래스 추가
    - title:String
    - description:String
    - memberNo:int
    - projectNo:int
    - state:int
  => bitcamp.pms.controller.TaskController 클래스 추가
  => "go task" 명령을 처리할 수 있도록 ProjectApp 클래스를 변경
*/
package bitcamp.pms;

import java.util.Scanner;
import bitcamp.pms.controller.MemberController;
import bitcamp.pms.controller.ProjectController;

public class ProjectApp {
  static Scanner keyScan = new Scanner(System.in);
  static MemberController memberController = new MemberController();
  static ProjectController projectController = new ProjectController();

  public static void main(String[] args) {
    memberController.setScanner(keyScan); // <-- 의존 객체 주입
    projectController.setScanner(keyScan);

    String input;
    do {
      input = prompt();
      processCommand(input);
    } while (!input.equals("quit"));

    keyScan.close(); // 항상 다 쓴 자원은 해제해야 한다.
  }

  static void processCommand(String input) {
    String[] cmds = input.split(" ");

    if (cmds[0].equals("quit")) {
      doQuit();
    } else if (cmds[0].equals("about")) {
      doAbout();
    } else if (cmds[0].equals("go")) {
      doGo(cmds);
    } else {
      doError();
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

  static void doGo(String[] cmds) {
    if (cmds.length < 2) {
      System.out.println("메뉴 이름을 지정하세요.");
      System.out.println("예) go member");
      return ;
    }

    switch (cmds[1]) {
      case "member":
        memberController.service();
        break;
      case "project":
        projectController.service();
        break;
      default:
        System.out.println("없는 메뉴입니다.");
    }

  }
}
