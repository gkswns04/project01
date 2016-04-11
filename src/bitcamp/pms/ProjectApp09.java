/* 목표
- persistence framework "mybatis" 적용
- 로그인 기능 추가
  => req-33.txt 파일 참조 
  
- 작업절차
1) 초기 메뉴 출력 

*/
package bitcamp.pms;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bitcamp.pms.context.ApplicationContext;
import bitcamp.pms.context.request.RequestHandler;
import bitcamp.pms.context.request.RequestHandlerMapping;

public class ProjectApp09 {
  static ApplicationContext appContext;
  static RequestHandlerMapping requestHandlerMapping;
  static Scanner keyScan = new Scanner(System.in);

  public static void main(String[] args) {
    appContext = new ApplicationContext("bitcamp.pms");
    requestHandlerMapping = new RequestHandlerMapping(appContext);

    // 명령을 처리하는 메서드에서 keyScan을 사용할 수 있도록 
    // ApplicationContext에 추가한다.
    appContext.addBean("stdinScan", keyScan);
    
    // mybatis SqlSessionFactory 객체 준비
    try {
      InputStream inputStream = Resources.getResourceAsStream(
          "conf/mybatis-config.xml");
      appContext.addBean("sqlSessionFactory", 
          new SqlSessionFactoryBuilder().build(inputStream));
    } catch (Exception e) {
      System.out.println("mybatis 준비 중 오류 발생!\n시스템을 종료하겠습니다.");
      e.printStackTrace();
      return;
    }
    
    processAuthentication();
    
    String input;
    do {
      input = prompt();
      processCommand(input);
    } while (!input.equals("quit"));

    keyScan.close(); // 항상 다 쓴 자원은 해제해야 한다.
  }

  private static void processAuthentication() {
    String input = null;
    while (true) {
      System.out.println("1) 로그인");
      System.out.println("2) 회원가입");
      System.out.println("9) 종료");
      System.out.print("선택? ");
      input = keyScan.nextLine();
      
      switch (input) {
      case "1":
        if (doLogin()) {
          return;
        }
        break;
      case "2":
        doSignUp();
        break;
      case "9":
        System.out.println("안녕히가세요");
        System.exit(0);
        break;
      default:
        System.out.println("올바르지 않은 번호입니다.");
      }
    }
  }

  private static void doSignUp() {
    System.out.print("이름: ");
    String name = keyScan.nextLine();
    
    System.out.print("이메일: ");
    String email = keyScan.nextLine();
    
    System.out.print("암호: ");
    String password = keyScan.nextLine();
    
    System.out.print("전화: ");
    String tel = keyScan.nextLine();
  }

  private static boolean doLogin() {
    System.out.print("이메일: ");
    String email = keyScan.nextLine();
    
    System.out.print("암호: ");
    String password = keyScan.nextLine();
    
    if (email.equals("aaaa") && password.equals("1111")) {
      return true;
    } else {
      System.out.println("이메일 또는 암호가 맞지 않습니다.");
      return false;
    }
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
