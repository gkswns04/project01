package bitcamp.pms.controller;

import java.util.Scanner;
import bitcamp.pms.domain.Project;
import bitcamp.pms.util.LinkedList;
import bitcamp.pms.exception.OutOfIndexException;
import java.sql.Date;


public class ProjectController {
  Scanner key = new Scanner(System.in);

  Project project;
  LinkedList<Project> projects = new LinkedList<>();
  String check = null;
  int no;
  public void service() {
    while(true){
      System.out.print("회원관리> ");
      check = key.nextLine().toLowerCase();
      try {
        switch(check) {
          case "add":
          project = new Project();
          System.out.print("제목? ");
          project.setTitle(key.nextLine());
          System.out.print("시작일? ");
          project.setStartDate(Date.valueOf(key.nextLine()));
          System.out.print("종료일? ");
          project.setEndDate(Date.valueOf(key.nextLine()));
          System.out.print("설명? ");
          project.setDescription(key.nextLine());
          if(confirm("저장")) {
            projects.add(project);

          }
          project = null;
          break;

          case "delete":
          System.out.println("삭제할 번호?");
          no = Integer.parseInt(key.nextLine());

          if(confirm("삭제")) {
            projects.remove(no);
          }
          break;

          case "update":
          project = new Project();
          System.out.print("변경할 번호?");

          System.out.println(projects.get(Integer.parseInt(key.nextLine())));
          System.out.println("-----------------------");
          System.out.print("제목? ");
          project.setTitle(key.nextLine());
          System.out.print("시작일? ");
          project.setStartDate(Date.valueOf(key.nextLine()));
          System.out.print("종료일? ");
          project.setEndDate(Date.valueOf(key.nextLine()));
          System.out.print("설명? ");
          project.setDescription(key.nextLine());
          if(confirm("변경")) {
            projects.set(no,project);

          }
          project = null;
          break;

          case "list":

          for(int i = 0; i <projects.size(); i++) {
           System.out.println(i +", "+ projects.get(i));
          }
          break;

          case "main":
          return ;
          default:
          System.out.println("잘못입력");
        }
      } catch (OutOfIndexException e) {
        System.out.println("인덱스 오류입니다");
      } catch(RuntimeException e) {
        System.out.println("잘못된 형식입니다");
      }
    }
  }

  public boolean confirm(String msg) {
    while(true) {
      System.out.println(msg + "하시겠습니까?(y/n) ");
      check = key.nextLine().toLowerCase();
      if(check.equals("y")) {
        System.out.println(msg + "합니다. ");
        return true;
      } else if(check.equals("n")) {
        System.out.println(msg + "을 취소합니다. ");
        return false;

      }else{
        System.out.println("잘못 입력");

      }
    }
  }
}
