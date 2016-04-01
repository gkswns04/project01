package bitcamp.pms.domain;

public class Member {
  protected String name;
  protected String email;
  protected String password;
  protected String tel;

  public Member() {}

  public Member(String name, String email, String password, String tel) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.tel = tel;
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getTel() {
    return this.tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }

  @Override
  public String toString() {
    return this.name + "," + this.email + "," +
            this.password + "," + this.tel;
  }


}
