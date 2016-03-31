package bitcamp.pms.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.domain.Board;

public class BoardDao {
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
  
  public void insert(Board board) throws Exception {
    FileWriter out0 = new FileWriter(filename, true);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    out.println(board.toCSV());

    out.close();
    out1.close();
    out0.close();
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
}
