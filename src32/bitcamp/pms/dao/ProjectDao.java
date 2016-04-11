package bitcamp.pms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Project;
import bitcamp.pms.util.DataSource;

@Component
public class ProjectDao {
  private DataSource dataSource;
  
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public List<Project> selectList() throws Exception {
    ArrayList<Project> list = new ArrayList<>();
    
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    try {
      con = dataSource.getConnection();
      stmt = con.createStatement();
      rs = stmt.executeQuery("select * from PROJECTS");
      Project project = null;
      
      while (rs.next()) { 
        project = new Project();
        project.setNo(rs.getInt("PNO"));
        project.setTitle(rs.getString("TITLE"));
        project.setStartDate(rs.getDate("SDT"));
        project.setEndDate(rs.getDate("EDT"));
        project.setState(rs.getInt("STAT"));
        project.setDescription(rs.getString("DESCT"));
        list.add(project);
      }
      return list;
    
    } finally {
      try {rs.close();} catch (Exception e) {}
      try {stmt.close();} catch (Exception e) {}
      dataSource.returnConnection(con);
    }
  }
  
  public Project selectOne(int no) throws Exception {
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
      con = dataSource.getConnection();
      stmt = con.prepareStatement("select * from PROJECTS where PNO=?");
      stmt.setInt(1, no);
      rs = stmt.executeQuery();
      
      if (rs.next()) { 
        Project project = new Project();
        project.setNo(rs.getInt("PNO"));
        project.setTitle(rs.getString("TITLE"));
        project.setStartDate(rs.getDate("SDT"));
        project.setEndDate(rs.getDate("EDT"));
        project.setState(rs.getInt("STAT"));
        project.setDescription(rs.getString("DESCT"));
        return project;
      }
      
      return null;
    
    } finally {
      try {rs.close();} catch (Exception e) {}
      try {stmt.close();} catch (Exception e) {}
      dataSource.returnConnection(con);
    }
  }
  
  public int insert(Project project) throws Exception {
    Connection con = null;
    PreparedStatement stmt = null;
    
    try {
      con = dataSource.getConnection();
      stmt = con.prepareStatement(
          "insert into PROJECTS(TITLE,SDT,EDT,DESCT) values(?,?,?,?)");

      stmt.setString(1, project.getTitle());
      stmt.setDate(2, project.getStartDate());
      stmt.setDate(3, project.getEndDate());
      stmt.setString(4, project.getDescription());
      
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
      dataSource.returnConnection(con);
    }
  }
  
  public int update(Project project) throws Exception {
    Connection con = null;
    PreparedStatement stmt = null;
    
    try {
      con = dataSource.getConnection();
      stmt = con.prepareStatement(
          "update PROJECTS set TITLE=?, SDT=?, EDT=?, DESCT=?, STAT=? where PNO=?");

      stmt.setString(1, project.getTitle());
      stmt.setDate(2, project.getStartDate());
      stmt.setDate(3, project.getEndDate());
      stmt.setString(4, project.getDescription());
      stmt.setInt(5, project.getState());
      stmt.setInt(6, project.getNo());
      
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
      stmt = con.prepareStatement("delete from PROJECTS where PNO=?");
      stmt.setInt(1, no);
      return stmt.executeUpdate();
      
    } finally {
      try {stmt.close();} catch (Exception e) {}
      dataSource.returnConnection(con);
    }
  }
}
