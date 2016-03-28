/* 목표
- High Cohesion OOP 설계 방법 적용
  => ProjectApp은 사용자에게 프롬프트를 출력하고 입력 받는 일도 하고,
     메뉴 처리기를 준비하는 일도 한다.
- 작업 절차
1) ProjectApp에서 메뉴 처리기를 준비하는 코드를 분리하여,
   ApplicationContext 클래스로 옮긴다.
   

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
      doError();
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
