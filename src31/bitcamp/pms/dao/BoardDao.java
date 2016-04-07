package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Board;

@Component
public class BoardDao {
  private Connection con;
  
  public void setConnection(Connection connection) {
    this.con = connection;
  }

  public List<Board> selectList() throws Exception {
    ArrayList<Board> list = new ArrayList<>();
    
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery("select * from BOARDS");
      Board board = null;
      
      while (rs.next()) { 
        board = new Board();
        board.setNo(rs.getInt("BNO"));
        board.setTitle(rs.getString("TITLE"));
        board.setContent(rs.getString("CONTS"));
        board.setViews(rs.getInt("VWCNT"));
        board.setPassword(rs.getString("PWD"));
        board.setCreatedDate(rs.getDate("CDT"));
        list.add(board);
      }
      return list;
    
    } finally {
      try {rs.close();} catch (Exception e) {}
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public Board selectOne(int no) throws Exception {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
      stmt = con.prepareStatement("select * from BOARDS where BNO=?");
      stmt.setInt(1, no);
      rs = stmt.executeQuery();
      
      if (rs.next()) { 
        Board board = new Board();
        board.setNo(rs.getInt("BNO"));
        board.setTitle(rs.getString("TITLE"));
        board.setContent(rs.getString("CONTS"));
        board.setViews(rs.getInt("VWCNT"));
        board.setPassword(rs.getString("PWD"));
        board.setCreatedDate(rs.getDate("CDT"));
        return board;
      }
      
      return null;
    
    } finally {
      try {rs.close();} catch (Exception e) {}
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public int insert(Board board) throws Exception {
    PreparedStatement stmt = null;
    
    try {
      stmt = con.prepareStatement(
          "insert into BOARDS(TITLE,CONTS,PWD,CDT)"
          + " values(?,?,?,now())");

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setString(3, board.getPassword());
      
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public int update(Board board) throws Exception {
    PreparedStatement stmt = null;
    
    try {
      stmt = con.prepareStatement(
          "update BOARDS set TITLE=?, CONTS=?, CDT=now() where BNO=?");

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());
      
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public int delete(int no) throws Exception {
    PreparedStatement stmt = null;
    
    try {
      stmt = con.prepareStatement("delete from BOARDS where BNO=?");
      stmt.setInt(1, no);
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
    }
  }
}










