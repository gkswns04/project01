package bitcamp.pms.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Board;

@Component("board/update.do")
public class BoardUpdateController implements MenuController {
  private static final String filename = "board.data";
  private Scanner keyScan;

  public List<Board> load() throws Exception {
    ArrayList<Board> boards = new ArrayList<>();
    
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
    
    return boards;
  }

  public void save(List<Board> boards) throws Exception {
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
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");

    try {
      List<Board> boards = this.load();
      
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
      
      this.save(boards);
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
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
