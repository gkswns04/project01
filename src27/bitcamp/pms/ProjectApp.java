/* 목표
- @RequestMapping 애노테이션을 추가한다.
  => 명령어를 처리하는 메서드를 지정한다.
  => MenuController 인터페이스가 필요없어 진다.
- XxxController는 MenuController 인터페이스를 구현하는 대신에
  @RequestMapping 애노테이션을 사용하여
  ProjectApp이 호출할 메서드를 지정한다.
  

- 작업절차
[DAO와 Controller를 구분]
1) @Controller 애노테이션 정의하기
  => 명령어를 처리하는 클래스를 지정하기 위함.
2) 컨트롤러 클래스 변경 
  => @Component 대신 @Controller를 붙인다.
3) ApplicationContext 클래스 변경
  => @Controller 애노테이션이 붙은 클래스에 대해서도 인스턴스 생성한다.

[MenuController 인터페이스 대신 @RequestMapping으로 호출할 메서드 구분]
1) @RequestMapping 애노테이션 정의하기
  => ProjectApp이 호출할 메서드에 대해 붙이는 애노테이션이다.
2) Controller 변경
  => MenuController 인터페이스 구현 문법을 제거한다.
  => service()에 @RequestMapping 애노테이션을 붙인다.
  => 메서드 이름도 프로그래밍의 일관성을 위해 명령어와 같게 한다.
3) MenuController 인터페이스를 삭제한다.

[명령어를 처리할 메서드 맵을 준비]
1) RequestHandler 클래스 작성
  => 메서드와 객체를 보관할 클래스이다.
2) RequestHandlerMapping 클래스 작성
  => ApplicationContext에 보관된 객체 중에서 @Controller 애노테이션이 붙은 
     객체만 꺼내서 @RequestMapping이 붙은 메서드를 추출하여 저장한다.
3) ProjectApp 클래스 변경
  => RequestHandlerMapping 클래스를 사용하여 명령어 처리하도록 코드를 변경한다.  
*/
package bitcamp.pms;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;

import bitcamp.pms.context.ApplicationContext;
import bitcamp.pms.context.request.RequestHandler;
import bitcamp.pms.context.request.RequestHandlerMapping;

public class ProjectApp {
  static ApplicationContext appContext;
  static RequestHandlerMapping requestHandlerMapping;
  static Scanner keyScan = new Scanner(System.in);

  public static void main(String[] args) {
    appContext = new ApplicationContext("bitcamp.pms");
    requestHandlerMapping = new RequestHandlerMapping(appContext);

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
      
      RequestHandler requestHandler = 
          (RequestHandler) requestHandlerMapping.getRequestHandler(cmds[0]);
      if (requestHandler != null) {
        // 작업에 필요한 재료를 준비
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("stdin", keyScan);
        
        Method method = requestHandler.getMethod();
        Object obj = requestHandler.getObj();
        try {
          method.invoke(obj, paramMap);
        } catch (Exception e) {
          System.out.println("명령 처리 중에 오류가 발생했습니다!");
        }
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
