package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Member;

@Component
public class MemberDao {
  private Connection con;
  
  public void setConnection(Connection connection) {
    this.con = connection;
  }
  
  public List<Member> selectList() throws Exception {
    ArrayList<Member> list = new ArrayList<>();
    
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery("select * from MEMBERS");
      Member member = null;
      
      while (rs.next()) { 
        member = new Member();
        member.setNo(rs.getInt("MNO"));
        member.setName(rs.getString("MNAME"));
        member.setEmail(rs.getString("EMAIL"));
        member.setPassword(rs.getString("PWD"));
        member.setTel(rs.getString("TEL"));
        list.add(member);
      }
      return list;
    
    } finally {
      try {rs.close();} catch (Exception e) {}
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public Member selectOne(int no) throws Exception {
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
      stmt = con.prepareStatement("select * from MEMBERS where MNO=?");
      stmt.setInt(1, no);
      rs = stmt.executeQuery();
      
      if (rs.next()) { 
        Member member = new Member();
        member.setNo(rs.getInt("MNO"));
        member.setName(rs.getString("MNAME"));
        member.setEmail(rs.getString("EMAIL"));
        member.setPassword(rs.getString("PWD"));
        member.setTel(rs.getString("TEL"));
        return member;
      }
      
      return null;
    
    } finally {
      try {rs.close();} catch (Exception e) {}
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public int insert(Member member) throws Exception {
    PreparedStatement stmt = null;
    
    try {
      stmt = con.prepareStatement("insert into MEMBERS(MNAME,EMAIL,PWD,TEL)"
          + " values(?,?,?,?)");

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, member.getTel());
      
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public int update(Member member) throws Exception {
    PreparedStatement stmt = null;
    
    try {
      stmt = con.prepareStatement(
          "update MEMBERS set MNAME=?, EMAIL=?, PWD=?, TEL=? where MNO=?");

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, member.getTel());
      stmt.setInt(5, member.getNo());
      
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
    }
  }
  
  public int delete(int no) throws Exception {
    PreparedStatement stmt = null;
    
    try {
      stmt = con.prepareStatement("delete from MEMBERS where MNO=?");
      stmt.setInt(1, no);
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
    }
  }
}

