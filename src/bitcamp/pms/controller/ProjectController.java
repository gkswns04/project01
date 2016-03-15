package bitcamp.pms.controller;

import java.util.Scanner;
import java.sql.Date;
import bitcamp.pms.domain.Project;
import bitcamp.pms.util.LinkedList;

public class ProjectController {
  private Scanner keyScan;
  private LinkedList<Project> projects;
  private int count = 0;
  static String input = null;

  public ProjectController() {
    projects = new LinkedList<Project>();
  }

  public void service() {
    while (true) {
      input = prompt();
      if (input.equals("main")) {
        break;
      } else if (input.equals("add")) {
        doAdd();
      } else if (input.equals("update")) {
        doUpdate();
      } else if (input.equals("delete")) {
        doDelete();
      } else if (input.equals("list")) {
        doList();
      } else {
        System.out.println("올바르지 않은 명령어입니다.");
      }
    }
  }

  private boolean confirm(String message) {
    while(true) {
      System.out.printf("%s?(y/n)", message);
      input = keyScan.nextLine().toLowerCase();
      if(input.equals("y")) {
        return true;
      } else if (input.equals("n")) {
        return false;
      } else {
        System.out.println("잘못된 명령입니다.");
      }
    }
  }

  private void doAdd() {
    Project project = new Project();
    project.setEndDate(Date.valueOf(keyScan.nextLine()));

    System.out.print("설명? ");
    project.setTitle(keyScan.nextLine());

    System.out.print("시작일? ");
    project.setStartDate(Date.valueOf(keyScan.nextLine()));

    System.out.print("종료일? ");
    project.setEndDate(Date.valueOf(keyScan.nextLine()));

    System.out.print("설명? ");
    project.setDescription(keyScan.nextLine());

    if (confirm("저장하시겠습니까?")) {
      projects.add(project);
      System.out.println("저장하였습니다.");
    } else {
      System.out.println("저장을 취소하였습니다.");
    }

  }

  private void doUpdate() {
    System.out.print("변경할 프로젝트 번호는? ");
    int no = Integer.parseInt(keyScan.nextLine());
    Project oldProject = (Project)projects.get(no);
    Project project = new Project();

    System.out.printf("프로젝트명(%s)? ", oldProject.getTitle());
    project.setTitle(keyScan.nextLine());

    System.out.printf("시작일(%s)? ", oldProject.getStartDate().toString());
    project.setStartDate(Date.valueOf(keyScan.nextLine()));

    System.out.printf("종료일(%s)? ", oldProject.getEndDate().toString());
    project.setEndDate(Date.valueOf(keyScan.nextLine()));

    System.out.printf("설명(%s)? ", oldProject.getDescription());
    project.setDescription(keyScan.nextLine());

    if (confirm("정말 변경하시겠습니까?")) {
      projects.set(no, project);
      System.out.println("변경하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  private void doDelete() {
    System.out.print("삭제할 프로젝트 번호는?");
    int no = Integer.parseInt(keyScan.nextLine());

    if (confirm("정말 삭제하시겠습니까?")) {
      projects.remove(no);
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("삭제를 취소하였습니다.");
    }
  }

  private void doList() {
    Project project = null;
    for (int i = 0; i < projects.size(); i++) {
      project = (Project)projects.get(i);
      System.out.printf("%d, %s\n", i, project.toString());
    }
  }

  public void setScanner(Scanner keyScan) {
    this.keyScan = keyScan;
  }

  private String prompt() {
    System.out.print("프로젝트관리> ");
    return keyScan.nextLine();
  }
}
