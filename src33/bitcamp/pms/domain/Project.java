package bitcamp.pms.domain;

import java.sql.Date;

public class Project {
  private int     no;
  private String  title;
  private Date    startDate;
  private Date    endDate;
  private String  description;
  private int     state;

  @Override
  public String toString() {
    return "Project [no=" + no + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
        + ", description=" + description + ", state=" + state + "]";
  }
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public int getState() {
    return state;
  }
  public void setState(int state) {
    this.state = state;
  }
}
