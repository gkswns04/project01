package bitcamp.pms.domain;

import java.sql.Date;

public class Project {
  String title;
  Date startDate;
  Date endDate;
  String description;

  public Project() {}

  public Project(String title, Date startDate, Date endDate, String description) {
    this.title = title;
    this.startDate = startDate ;
    this.endDate =endDate;
    this.description =description;
  }

  @Override
  public String toString() {
    return title + " , " + startDate.toString() + " , " +endDate.toString()  + " , " +description;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
