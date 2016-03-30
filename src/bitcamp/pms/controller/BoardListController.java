package bitcamp.pms.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Board;

@Component("board/list.do")
public class BoardListController implements MenuController {
  private static final String filename = "board.data";
  
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

  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    try {
      List<Board> boards = this.load();
      
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
