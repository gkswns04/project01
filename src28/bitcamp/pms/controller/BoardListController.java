package bitcamp.pms.controller;

import java.util.List;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;

@Controller
public class BoardListController {
  private BoardDao boardDao;

  public void setBoardDao(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @RequestMapping("board/list.do")
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
}
