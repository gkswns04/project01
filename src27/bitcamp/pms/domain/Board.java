package bitcamp.pms.domain;

import java.sql.Date;

public class Board {
  private String title;
  private String content;
  private int views;
  private String password;
  private Date createdDate;
  
  @Override
  public String toString() {
    return title + "," + views + "," + createdDate;
  }
  
  public String toCSV() {
    return String.format("%s,%s,%d,%s,%s", this.title, this.content, 
        this.views, this.password, this.createdDate.toString());
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getViews() {
    return views;
  }

  public void setViews(int views) {
    this.views = views;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  
  
}
