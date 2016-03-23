package bitcamp.pms;

import java.util.Scanner;
import bitcamp.pms.controller.ProjectController;

public class ProjectApp {
  public static void main(String[] args) {
    Scanner key = new Scanner(System.in);
    ProjectController project = new ProjectController();
    String check = null;

    while(true){
      System.out.print("명령> ");
      check = key.nextLine().toLowerCase();

      switch (check) {
        case "about":
          System.out.println("자바 80기 경연");
          break;
        case "quit":
          System.out.println("안녕히가세요");
          break;
        case "go project":
          project.service();
          break;
        default:
          System.out.println("잘못 입력");
      }
    }
  }
}
