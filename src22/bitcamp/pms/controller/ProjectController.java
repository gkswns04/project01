package bitcamp.pms.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Project;

@Component("project")
public class ProjectController implements MenuController {
  private static final String filename = "project.data";
  private Scanner keyScan;
  ArrayList<Project> projects;

  public ProjectController() {
    projects = new ArrayList<>();
  }

  public void load() throws Exception {
    FileReader in0 = new FileReader(filename);
    BufferedReader in = new BufferedReader(in0);

    String line;
    String[] values;
    Project project;
    while ((line = in.readLine()) != null) {
      values = line.split(",");
      project = new Project(values[0],
                            Date.valueOf(values[1]),
                            Date.valueOf(values[2]));
      project.setState(Integer.parseInt(values[3]));
      project.setDescription(values[4]);
      projects.add(project);
    }

    in.close();
    in0.close();
  }

  public void save() throws Exception {
    FileWriter out0 = new FileWriter(filename);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    for (Project project : projects) {
      out.println(project);
    }

    out.close();
    out1.close();
    out0.close();
  }

  @Override
  public void init() {
    try {
      this.load();
    } catch (Exception e) {
      throw new RuntimeException("프로젝트 데이터 로딩 실패!", e);
    }
  }

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");

    String input = null;
    while (true) {
      input = prompt();
      try {
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
      } catch (IndexOutOfBoundsException e) {
        System.out.println("유효하지 않은 인덱스입니다.");
      } catch (Exception e) {
        System.out.println("오류 발생! 다시 작업해 주세요.");
      }
    }
  }

  @Override
  public void destroy() {
    try {
      this.save();
    } catch (Exception e) {}
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
      projects.add(project);
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
    for (int i = 0; i < projects.size(); i++) {
      System.out.printf("%d, %s\n", i, projects.get(i).toString());
    }
  }

  private void doUpdate() {
    System.out.print("변경할 프로젝트 번호?");
    int no = Integer.parseInt(keyScan.nextLine());

    Project oldProject = projects.get(no);
    Project project = new Project();

    System.out.printf("프로젝트명(%s)? ", oldProject.getTitle());
    project.setTitle(keyScan.nextLine());
    System.out.printf("시작일(%s)? ", oldProject.getStartDate());
    project.setStartDate(Date.valueOf(keyScan.nextLine()));
    System.out.printf("종료일(%s)? ", oldProject.getEndDate());
    project.setEndDate(Date.valueOf(keyScan.nextLine()));
    System.out.printf("설명(%s)? ", oldProject.getDescription());
    project.setDescription(keyScan.nextLine());

    if (confirm("변경하시겠습니까?")) {
      projects.set(no, project);
      System.out.println("변경하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  private void doDelete() {
    System.out.print("삭제할 프로젝트 번호?");
    int no = Integer.parseInt(keyScan.nextLine());

    if (confirm("정말 삭제하시겠습니까?")) {
      projects.remove(no);
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("삭제를 취소하였습니다.");
    }
  }
}
