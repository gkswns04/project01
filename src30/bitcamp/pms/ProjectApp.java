/* 목표
- XxxDao에 JDBC를 적용하라!
  
- 작업절차
1) domain 클래스를 변경한다.
  => no 값을 저장할 수 있는 필드를 추가한다.
  => getter/setter 추가
  => toString() 재작성 

2) dao 클래스 변경
  => java01/src/step29/exam03/MemberDao.java 참고!
  => MemberDao 변경 --> MemberController 변경 --> 테스트
  => BoardDao 변경 --> BoardController 변경 --> 테스트
  => ProjectDao 변경 --> ProjectController 변경 --> 테스트
  


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
        e.printStackTrace();
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
