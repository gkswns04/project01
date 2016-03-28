/* 목표
- 리플랙션(Reflection) API를 사용하여 메뉴 컨트롤러를 객체를 자동 생성하라!
  => 인스턴스 생성을 자동화시킨다.
- 즉 메뉴 처리기 생성을 자동화시킨다.

*/
package bitcamp.pms;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import bitcamp.pms.controller.MenuController;

public class ProjectApp {
  static HashMap<String,MenuController> menuMap = new HashMap<>();
  static Scanner keyScan = new Scanner(System.in);

  static void prepareObject(String packageName) {
    String path = "./bin/" + packageName.replace(".", "/");
    createObject(new File(path));
  }
  
  static void createObject(File file) {
    if (file.isFile() && file.getName().endsWith(".class")) {
      String classNameWithPackage = file.getPath()  // ex) ./bin/bitcamp/pms/controller/MemberController.class
                        .replace("./bin/", "") // --> bitcamp/pms/controller/MemberController.class
                        .replace(".class","") // --> bitcamp/pms/controller/MemberController
                        .replace("/", "."); // --> bitcamp.pms.controller.MemberController
      try {
        Class<?> clazz = Class.forName(classNameWithPackage);
        if (!clazz.isInterface()) {
          Object obj = clazz.newInstance();
          if (MenuController.class.isInstance(obj)) {
            menuMap.put(obj.toString(), (MenuController)obj);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    } 
    
    File[] subfiles = file.listFiles();
    for (File subfile : subfiles) {
      createObject(subfile);
    }
  }
  
  public static void main(String[] args) {
    // bitcamp.controller 패키지를 뒤져서 메뉴 처리기 객체를 생성하고,
    // 그 메뉴 처리기 객체를 menuMap에 등록한다.
    
    // 1) 현재 폴더 밑에 있는 bin 폴더에서 bitcamp/pms/controller 패키지에 있는
    // 모든 클래스의 이름을 알아낸다.
    // 2) MenuController의 구현체를 찾아 인스턴스를 생성한다.
    // 3) 그 인스턴스를 menuMap에 보관한다.
    prepareObject("bitcamp.pms.controller");
    
    
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
