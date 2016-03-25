/* 목표
- 코드 구조를 개선하기
- 메뉴를 처리하기 위해 호출하는 service() 메서드를 규칙으로 정의한다.
  => 향후 메뉴를 처리하는 모든 클래스는 이 인터페이스의 규칙에 따라 작성해야 한다.
- 작업
1) MenuController 인터페이스 정의
2) 기존의 컨트롤러 클래스를 변경한다.
  - MenuController 인터페이스를 구현한다.
3) 메뉴 처리 객체를 맵으로 관리한다.
  - 메뉴 이름을 메뉴를 처리하는 컨트롤러를 보관한다.
  - 사용할 때는 맵에서 찾아 꺼내 쓴다.
4) 작업을 종료 후 메뉴 처리기에게 자원 해제의 기회를 준다.
  - destroy() 호출.
5) 작업을 시작하기 전에 메뉴 처리기에게 준비할 수 있는 기회를 준다.
  - init() 호출
6) 작업에 필요한 재료를 service() 메서드의 파라미터로 넘기도록 한다.
  - service() --> service(Map paramMap) 으로 변경

*/
package bitcamp.pms;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Collection;
import bitcamp.pms.controller.MenuController;
import bitcamp.pms.controller.MemberController;
import bitcamp.pms.controller.ProjectController;

public class ProjectApp {
  static HashMap<String,MenuController> menuMap = new HashMap<>();
  static Scanner keyScan = new Scanner(System.in);

  public static void main(String[] args) {
    // 메뉴 처리기를 menuMap에 등록한다.
    menuMap.put("member", new MemberController());
    menuMap.put("project", new ProjectController());

    Collection<MenuController> controllers = menuMap.values();

    for (MenuController controller : controllers) {
      try {
        controller.init();
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
        //e.printStackTrace();
      }
    }

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
    Collection<MenuController> controllers = menuMap.values();
    for (MenuController controller : controllers) {
      controller.destroy();
    }
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

    // 작업에 필요한 재료를 준비
    HashMap<String,Object> paramMap = new HashMap<>();
    paramMap.put("stdin", keyScan);

    MenuController controller = menuMap.get(cmds[1]);
    if (controller != null) {
      controller.service(paramMap);
    } else {
      System.out.println("없는 메뉴입니다.");
    }
  }
}
