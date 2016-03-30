package bitcamp.pms.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Board;

@Component("board/add.do")
public class BoardAddController implements MenuController {
  private static final String filename = "board.data";
  private Scanner keyScan;

  public void insert(Board board) throws Exception {
    FileWriter out0 = new FileWriter(filename, true);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    out.println(board.toCSV());

    out.close();
    out1.close();
    out0.close();
  }

  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");

    Board board = new Board();

    System.out.print("제목? ");
    board.setTitle(keyScan.nextLine());
    System.out.print("내용? ");
    board.setContent(keyScan.nextLine());
    System.out.print("암호? ");
    board.setPassword(keyScan.nextLine());
    board.setCreatedDate(new Date(System.currentTimeMillis()));
    
    if (confirm("저장하시겠습니까?")) {
      try {
        this.insert(board);
        System.out.println("저장하였습니다.");
      } catch (Exception e) {
        System.out.println("데이터를 저장할 수 없습니다.");
      }
    } else {
      System.out.println("저장을 취소하였습니다.");
    }
  }

  @Override
  public void destroy() {}

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

}
