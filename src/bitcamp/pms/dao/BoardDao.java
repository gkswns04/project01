package bitcamp.pms.dao;

import bitcamp.pms.annotation.Component;

@Component
public class BoardDao {
/*

  public List<Board> selectList() throws Exception {
    ArrayList<Board> list = new ArrayList<>();
    
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      con = dataSource.getConnection();
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
      dataSource.returnConnection(con);
    }
  }
  
  public Board selectOne(int no) throws Exception {
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
      con = dataSource.getConnection();
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
      dataSource.returnConnection(con);
    }
  }
  
  public int insert(Board board) throws Exception {
    Connection con = null;
    PreparedStatement stmt = null;
    
    try {
      con = dataSource.getConnection();
      stmt = con.prepareStatement(
          "insert into BOARDS(TITLE,CONTS,PWD,CDT)"
          + " values(?,?,?,now())");

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setString(3, board.getPassword());
      
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
      dataSource.returnConnection(con);
    }
  }
  
  public int update(Board board) throws Exception {
    Connection con = null;
    PreparedStatement stmt = null;
    
    try {
      con = dataSource.getConnection();
      stmt = con.prepareStatement(
          "update BOARDS set TITLE=?, CONTS=?, CDT=now() where BNO=?");

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());
      
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
      dataSource.returnConnection(con);
    }
  }
  
  public int delete(int no) throws Exception {
    Connection con = null;
    PreparedStatement stmt = null;
    
    try {
      con = dataSource.getConnection();
      stmt = con.prepareStatement("delete from BOARDS where BNO=?");
      stmt.setInt(1, no);
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
      dataSource.returnConnection(con);
    }
  }
  */
}










