package bitcamp.pms.controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;
import bitcamp.pms.util.CommandUtil;

@Controller
@RequestMapping("board/") // 매핑 정보 일부를 선언한다.
public class BoardController {
  private BoardDao boardDao;

  public void setBoardDao(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @RequestMapping("add.do") // 매핑 정보 나머지를 선언한다.
  public void add(Scanner keyScan) {
    Board board = new Board();

    System.out.print("제목? ");
    board.setTitle(keyScan.nextLine());
    System.out.print("내용? ");
    board.setContent(keyScan.nextLine());
    System.out.print("암호? ");
    board.setPassword(keyScan.nextLine());
    board.setCreatedDate(new Date(System.currentTimeMillis()));
    
    if (CommandUtil.confirm(keyScan, "저장하시겠습니까?")) {
      try {
        boardDao.insert(board);
        System.out.println("저장하였습니다.");
      } catch (Exception e) {
        System.out.println("데이터를 저장할 수 없습니다.");
      }
    } else {
      System.out.println("저장을 취소하였습니다.");
    }
  }
  
  @RequestMapping("delete.do")
  public void delete(Scanner keyScan) {
    try {
      System.out.print("삭제할 게시물 번호?");
      int no = Integer.parseInt(keyScan.nextLine());
  
      if (CommandUtil.confirm(keyScan, "정말 삭제하시겠습니까?")) {
        boardDao.delete(no);
        System.out.println("삭제하였습니다.");
      } else {
        System.out.println("삭제를 취소하였습니다.");
      }
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
      e.printStackTrace();
    } 
  }
  
  @RequestMapping("list.do")
  public void list() {
    try {
      List<Board> boards = boardDao.selectList();
      
      for (int i = 0; i < boards.size(); i++) {
        System.out.printf("%d, %s\n", i, boards.get(i).toString());
      }
    } catch (Exception e) {
      throw new RuntimeException("게시물 데이터 로딩 실패!", e);
    }
  }
  
  @RequestMapping("update.do")
  public void update(Scanner keyScan) {
    try {
      System.out.print("변경할 게시물 번호?");
      int no = Integer.parseInt(keyScan.nextLine());
      
      Board oldBoard = boardDao.selectOne(no);
      Board board = new Board();
  
      System.out.printf("제목(%s)? ", oldBoard.getTitle());
      board.setTitle(keyScan.nextLine());
      System.out.printf("내용(%s)? ", oldBoard.getContent());
      board.setContent(keyScan.nextLine());
      System.out.printf("암호? ", oldBoard.getPassword());
      board.setPassword(keyScan.nextLine());
      board.setCreatedDate(new Date(System.currentTimeMillis()));
  
      if (CommandUtil.confirm(keyScan, "변경하시겠습니까?")) {
        boardDao.update(no, board);
        System.out.println("변경하였습니다.");
      } else {
        System.out.println("변경을 취소하였습니다.");
      }
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
    }
  }
}
