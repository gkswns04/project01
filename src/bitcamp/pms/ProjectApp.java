/* 목표
- spring framework 적용

[작업]
1) 스프링 및 마이바티스 의존 라이브러리 추가
  => build.gradle 파일 변경.
  => gradle을 실행하여 이클립스 설정 파일을 갱신.

2) 스프링 설정 파일 추가
  => conf/application-context.xml 파일.

3) 마이바티스 맵퍼 파일 변경
  => 맵퍼 파일의 넴임스페이스를 인터페이스 이름(패키지명 포함)과 같게 한다.
  => 마이바티스 설정 파일(conf/mybatis-config.xml)을 제거한다.

4) DAO 클래스를 인터페이스로 변경한다.
  => BoardDao, MemberDao, ProjectDao
  => 인터페이스의 메서드 시그너처(메서드 이름과 파라미터, 리턴 타입)는
     맵퍼 파일의 SQL id, parameterType, resultType과 같아야 한다.

5) 컨트롤러 클래스를 개정한다.
  => DAO 인터페이스에 선언된 메서드가 변경되었기 때문에 그에 맞추어
     컨트롤러 클래스의 코드를 변경한다. 
     => DAO 의존 객체를 주입하려면 @Autowired 애노테이션을 붙여야 한다.
  => 기존의 @Controller 애노테이션을 스프링 애노테이션으로 교체한다.
     => import 변경
  => 기존의 @Controller, @Component 애노테이션을 제거한다.
     bitcamp.pms.annotation.Component
     bitcamp.pms.annotation.Controller 
     
4) 빈 컨테이너를 스프링 IoC 컨테이너로 교체한다.
  => 기존의 ApplicationContext를 제거한다.
     bitcamp.pms.context.ApplicationContext
  => 기존의 ApplicationContext를 스프링의 ApplicationContext로 바꾼다.
  => ProjectApp 클래스 변경한다.
*/
package bitcamp.pms;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bitcamp.pms.context.request.RequestHandler;
import bitcamp.pms.context.request.RequestHandlerMapping;
import bitcamp.pms.controller.AuthController;
import bitcamp.pms.util.Session;


//=> 정리!
// static 필드나 메서드를 인스턴스 필드와 메서드로 전환한다.
public class ProjectApp {
  ApplicationContext appContext;
  RequestHandlerMapping requestHandlerMapping;
  Scanner keyScan = new Scanner(System.in);
  Session session = new Session();
  
  public static void main(String[] args) {
    ProjectApp projectApp = new ProjectApp();
    projectApp.run();
  }
  
  public ProjectApp() {
    appContext = new ClassPathXmlApplicationContext(
        "conf/application-context.xml");
    requestHandlerMapping = new RequestHandlerMapping(appContext);
  }

  public void run() {
    AuthController authController = 
        (AuthController)appContext.getBean(AuthController.class);
    authController.service(keyScan, session);
    
    String input;
    do {
      input = prompt();
      processCommand(input);
    } while (!input.equals("quit"));
    
    keyScan.close(); // 항상 다 쓴 자원은 해제해야 한다.
  }
  
  void processCommand(String input) {
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
          if (param.getType() == Scanner.class) {
            arg = keyScan;
          } else if (param.getType() == Session.class) {
            arg = session;
          } else {
            arg = appContext.getBean(param.getType());
          }
          
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

  private String prompt() {
    System.out.print("명령> ");
    return keyScan.nextLine().toLowerCase();
  }

  private void doQuit() {
    System.out.println("안녕히 가세요!");
  }

  private void doError() {
    System.out.println("올바르지 않은 명령어입니다.");
  }

  private void doAbout() {
    System.out.println("비트캠프 80기 프로젝트 관리 시스템!");
  }

}
