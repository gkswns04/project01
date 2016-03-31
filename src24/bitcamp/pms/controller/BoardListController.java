package bitcamp.pms.controller;

import java.util.List;
import java.util.Map;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;

@Component("board/list.do")
public class BoardListController implements MenuController {
  BoardDao boardDao = new BoardDao();
  
  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    try {
      List<Board> boards = boardDao.load();
      
      for (int i = 0; i < boards.size(); i++) {
        System.out.printf("%d, %s\n", i, boards.get(i).toString());
      }
    } catch (Exception e) {
      throw new RuntimeException("게시물 데이터 로딩 실패!", e);
    }
  }

  @Override
  public void destroy() {}
  
}
