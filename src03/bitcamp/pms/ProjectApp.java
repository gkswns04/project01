/* 목표
- 다음과 같이 회원 정보를 입력 받아 출력하라!
이름? 홍길동
이메일? hong@test.com
암호? 1111
전화? 111-1111
------------------------
홍길동, hong@test.com, 1111, 111-1111
*/
package bitcamp.pms;

import java.util.Scanner;

public class ProjectApp {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("이름? ");
    String name = keyScan.nextLine();

    System.out.print("이메일? ");
    String email = keyScan.nextLine();

    System.out.print("암호? ");
    String password = keyScan.nextLine();

    System.out.print("전화? ");
    String tel = keyScan.nextLine();

    System.out.println("--------------------------------");
    System.out.printf("%s, %s, %s, %s\n", name, email, password, tel);
  }
}
