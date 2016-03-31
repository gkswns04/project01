/* 목표
- 커맨드 패턴을 적용하여 명령어 당 한 개의 처리 클래스로 구성하라. 
예)
[이전 방식]
명령> go member
프로젝트관리> list

[변경 방식]
명령> member/list.do
.....
명령> member/add.do
.....

- 작업절차
1) MemberController 클래스를 다음 클래스들로 분리한다.
   MemberListController, MemberAddController, MemberUpdateController, 
   MemberDeleteController
   

*/
package bitcamp.pms;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import bitcamp.pms.context.ApplicationContext;
import bitcamp.pms.controller.MenuController;

public class ProjectApp {
  static ApplicationContext appContext;
  static Scanner keyScan = new Scanner(System.in);

  public static void main(String[] args) {
    appContext = new ApplicationContext("bitcamp.pms.controller");
    
    List<Object> controllers = 
        appContext.getBeans(MenuController.class);

    for (Object controller : controllers) {
      try {
        ((MenuController)controller).init();
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
      MenuController controller = (MenuController)appContext.getBean(cmds[0]);
      if (controller != null) {
        // 작업에 필요한 재료를 준비
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("stdin", keyScan);
        
        controller.service(paramMap);
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
    List<Object> controllers = 
        appContext.getBeans(MenuController.class);

    for (Object controller : controllers) {
        ((MenuController)controller).destroy();
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

    MenuController controller = (MenuController)appContext.getBean(cmds[1]);
    if (controller != null) {
      controller.service(paramMap);
    } else {
      System.out.println("없는 메뉴입니다.");
    }
  }
}
