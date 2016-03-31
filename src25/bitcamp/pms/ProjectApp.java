/* 목표
- Information Expert 패턴에 따라 XxxDao 클래스가 데이터 처리를
  완전히 담당하게 개선하라!
  

- 작업절차
1) XxxDao 클래스에 update(), delete(), selectOne() 메서드를 생성한다.
2) XxxDao 클래스의 load() 메서드 이름을 selectList() 이름으로 바꾼다.
3) XxxController 클래스를 DAO에 맞추어 변경한다.
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

}
