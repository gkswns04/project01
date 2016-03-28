/* 기능 요구사항
 * 1) list    --> 제목, 조회수, 생성일
 * 2) add     --> 제목, 내용, 암호
 * 3) update  --> 제목, 내용, 암호
 * 4) delete  --> 인덱스로 삭제할 게시물을 지정한다.
 */
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

import bitcamp.pms.domain.Board;

public class BoardController implements MenuController {
  private static final String filename = "board.data";
  private Scanner keyScan;
  ArrayList<Board> boards;

  public BoardController() {
    boards = new ArrayList<>();
  }
  
  @Override
  public String toString() {
    return "board";
  }

  public void load() throws Exception {
    FileReader in0 = new FileReader(filename);
    BufferedReader in = new BufferedReader(in0);

    String line;
    String[] values;
    Board board;
    while ((line = in.readLine()) != null) {
      values = line.split(",");
      board = new Board();
      board.setTitle(values[0]);
      board.setContent(values[1]);
      board.setViews(Integer.parseInt(values[2]));
      board.setPassword(values[3]);
      board.setCreatedDate(Date.valueOf(values[4]));

      boards.add(board);
    }

    in.close();
    in0.close();
  }

  public void save() throws Exception {
    FileWriter out0 = new FileWriter(filename);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    for (Board board : boards) {
      out.println(board.toCSV());
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
      throw new RuntimeException("게시물 데이터 로딩 실패!", e);
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
    System.out.print("게시물관리> ");
    return keyScan.nextLine();
  }

  private void doAdd() {
    Board board = new Board();

    System.out.print("제목? ");
    board.setTitle(keyScan.nextLine());
    System.out.print("내용? ");
    board.setContent(keyScan.nextLine());
    System.out.print("암호? ");
    board.setPassword(keyScan.nextLine());
    board.setCreatedDate(new Date(System.currentTimeMillis()));
    
    if (confirm("저장하시겠습니까?")) {
      boards.add(board);
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
    for (int i = 0; i < boards.size(); i++) {
      System.out.printf("%d, %s\n", i, boards.get(i).toString());
    }
  }

  private void doUpdate() {
    System.out.print("변경할 게시물 번호?");
    int no = Integer.parseInt(keyScan.nextLine());

    Board oldBoard = boards.get(no);
    Board board = new Board();

    System.out.printf("제목(%s)? ", oldBoard.getTitle());
    board.setTitle(keyScan.nextLine());
    System.out.printf("내용(%s)? ", oldBoard.getContent());
    board.setContent(keyScan.nextLine());
    System.out.printf("암호? ", oldBoard.getPassword());
    board.setPassword(keyScan.nextLine());
    board.setCreatedDate(new Date(System.currentTimeMillis()));

    if (confirm("변경하시겠습니까?")) {
      boards.set(no, board);
      System.out.println("변경하였습니다.");
    } else {
      System.out.println("변경을 취소하였습니다.");
    }
  }

  private void doDelete() {
    System.out.print("삭제할 게시물 번호?");
    int no = Integer.parseInt(keyScan.nextLine());

    if (confirm("정말 삭제하시겠습니까?")) {
      boards.remove(no);
      System.out.println("삭제하였습니다.");
    } else {
      System.out.println("삭제를 취소하였습니다.");
    }
  }
}
