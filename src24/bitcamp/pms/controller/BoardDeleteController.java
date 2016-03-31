package bitcamp.pms.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;
import bitcamp.pms.util.CommandUtil;

@Component("board/delete.do")
public class BoardDeleteController implements MenuController {
  BoardDao boardDao = new BoardDao();
  
  private Scanner keyScan;

  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");
    
    try {
      List<Board> boards = boardDao.load();
      
      System.out.print("삭제할 게시물 번호?");
      int no = Integer.parseInt(keyScan.nextLine());
  
      if (CommandUtil.confirm(keyScan, "정말 삭제하시겠습니까?")) {
        boards.remove(no);
        System.out.println("삭제하였습니다.");
      } else {
        System.out.println("삭제를 취소하였습니다.");
      }
      
      boardDao.save(boards);
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
      e.printStackTrace();
    } 
  }

  @Override
  public void destroy() {}

}
