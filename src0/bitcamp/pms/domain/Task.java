package bitcamp.pms.domain;

import java.sql.Date;

public class Task {
  private int no;
  private String workerEmail;
  private String title;
  private String content;
  private Date startDate;
  private Date endDate;
  private int state;

  public Task() {
    super();
    // TODO Auto-generated constructor stub
  }

  public Task(int no, String workerEmail, String title, String content, Date startDate, Date endDate, int state) {
    super();
    this.no = no;
    this.workerEmail = workerEmail;
    this.title = title;
    this.content = content;
    this.startDate = startDate;
    this.endDate = endDate;
    this.state = state;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getWorkerEmail() {
    return workerEmail;
  }

  public void setWorkerEmail(String workerEmail) {
    this.workerEmail = workerEmail;
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

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return no + ", " + workerEmail + ", " + title + ", " + content
        + ", " + startDate + ", " + endDate + ", " + state;
  }

}
