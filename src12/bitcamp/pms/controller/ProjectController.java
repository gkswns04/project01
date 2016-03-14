package bitcamp.pms.controller;

import java.util.Scanner;
import java.sql.Date;
import bitcamp.pms.domain.Project;

public class ProjectController {
  private Scanner keyScan;
  private Project[] projects = new Project[1000];
  private int count;

  public void setScanner(Scanner keyScan) {
    this.keyScan = keyScan;
  }

  public void service() {
    String input = null;
    while (true) {
      input = prompt();
      if (input.equals("main")) {
        break;
      } else if (input.equals("add")) {
        doAdd();
      } else if (input.equals("list")) {
        doList();
      } else if (input.equals("update")) {
        doUpdate();
      } else if (input.equals("delete")) {
        doDelete();
      } else {
        System.out.println("지원하지 않는 명령어입니다.");
      }

    }
  }

  private String prompt() {
    System.out.print("프로젝트관리> ");
    return keyScan.nextLine();
  }

  private void doAdd() {
    Project project = new Project();

    System.out.print("프로젝트명? ");
    project.setTitle(keyScan.nextLine());
    System.out.print("시작일? ");
    project.setStartDate(Date.valueOf(keyScan.nextLine()));
    System.out.print("종료일? ");
    project.setEndDate(Date.valueOf(keyScan.nextLine()));
    System.out.print("설명? ");
    project.setDescription(keyScan.nextLine());

    if (confirm("저장하시겠습니까?")) {
      projects[count++] = project;
      System.out.println("저장하였습니다.");
    } else {
      System.out.println("저장을 취소하였습니다.");
    }
  }

  private boolean confirm(String message) {
    while (true) {
      System.out.printf("%s(y/n) ", message);
      String input = keyScan.nextLine().toLowerCase();
      if (input.equals("y")) {
        return true;
      } else if (input.equals("n")) {
        return false;
      } else {
        System.out.println("잘못된 명령어입니다.");
      }
    }
  }

  private void doList() {
    for (int i = 0; i < count; i++) {
      System.out.printf("%d, %s\n", i, projects[i].toString());
    }
  }

  private void doUpdate() {
    System.out.print("변경할 프로젝트 번호?");
    int no = Integer.parseInt(keyScan.nextLine());

    Project project = new Project();

    System.out.printf("프로젝트명(%s)? ", projects[no].getTitle());
    project.setTitle(keyScan.nextLine());
    System.out.printf("시작일(%s)? ", projects[no].getStartDate());
    project.setStartDate(Date.valueOf(keyScan.nextLine()));
    System.out.printf("종료일(%s)? ", projects[no].getEndDate());
    project.setEndDate(Date.valueOf(keyScan.nextLine()));
    System.out.printf("설명(%s)? ", projects[no].getDescription());
    project.setDescription(keyScan.nextLine());

    if (confirm("변경하시겠습니까?")) {
      projects[no] = project;
      System.out.println("변경하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  private void doDelete() {
    System.out.print("삭제할 프로젝트 번호?");
    int no = Integer.parseInt(keyScan.nextLine());

    if (confirm("정말 삭제하시겠습니까?")) {
      for (int i = no; i < (count - 1); i++) {
        projects[i] = projects[i+1];
      }
      count--;
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("삭제를 취소하였습니다.");
    }
  }








}
