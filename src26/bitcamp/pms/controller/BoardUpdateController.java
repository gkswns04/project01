package bitcamp.pms.controller;

import java.sql.Date;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;
import bitcamp.pms.util.CommandUtil;

@Component("board/update.do")
public class BoardUpdateController implements MenuController {
  private BoardDao boardDao;
  private Scanner keyScan;

  public void setBoardDao(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");

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

  @Override
  public void destroy() {}

}
