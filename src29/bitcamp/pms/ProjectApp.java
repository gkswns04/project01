/* 목표
- 여러 클래스에 분산되어 있는 CRUD(Create/Read/Update/Delete)관련
  메서드를 한 클래스로 합친다.
- 반드시 합쳐야 하는 것은 아니다.
- 합치는 것이 유지보수에 낫다고 판단되면 합쳐라!
- 컨트롤러 클래스에 @RequestMapping 이 있다면, 메서드를 정보를 저장할 때
  클래스에 설정된 @RequestMapping 값과 합쳐서 명령어 정보를 저장하라!

- 작업절차
1) BoardAddController의 클래스 이름을 BoardController로 변경한다.
2) BoardDeleteController, BoardListController, BoardUpdateController의
   메서드를 BoardController 클래스로 옮기고,
   기존 클래스들은 삭제한다.
  => 파라미터를 분석하여 메서드에 넘겨 줄 값을 준비하는 코드를 추가한다.
3) 나머지 MemberXxxController와 ProjectXxxController도 위와 같이 작업한다.

[매핑 정보 합치기]
1) 컨트롤러 클래스에 @RequestMapping을 붙인다.
2) RequestHandlerMapping 클래스를 변경한다.
  => 만약 클래스에 @RequestMapping이 붙어 있다면,
     메서드를 테이블에 저장할 때 이 정보를 포함한다.

*/
package bitcamp.pms;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
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

    // 명령을 처리하는 메서드에서 keyScan을 사용할 수 있도록
    // ApplicationContext에 추가한다.
    appContext.addBean("stdinScan", keyScan);

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

      if (requestHandler == null) { // 명령 처리기를 못 찾았다면,
        doError();
        return;
      }

      Method method = requestHandler.getMethod();
      Object obj = requestHandler.getObj();
      try {
        //1) 파라미터의 값을 담을 List를 준비한다.
        ArrayList<Object> args = new ArrayList<>();

        //2) 메서드의 파라미터 정보를 알아낸다.
        Parameter[] params = method.getParameters();
        Object arg = null;

        for (Parameter param : params) {
          //3) 파라미터에 해당하는 객체가 ApplicationContext에 있는지 알아본다.
          arg = appContext.getBean(param.getType());

          //4) 찾은 값을 아규먼트 목록에 담는다. 못 찾았으면 null을 담는다.
          args.add(arg);
        }

        //5) 준비한 값을 가지고 메서드를 호출한다.
        method.invoke(obj, args.toArray());

      } catch (Exception e) {
        System.out.println("명령 처리 중에 오류가 발생했습니다!");
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
