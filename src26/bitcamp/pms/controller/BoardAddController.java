package bitcamp.pms.controller;

import java.sql.Date;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;
import bitcamp.pms.util.CommandUtil;

@Component("board/add.do")
public class BoardAddController implements MenuController {
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

  @Override
  public void destroy() {}

}
