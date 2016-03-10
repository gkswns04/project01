/* 목표
- 다음과 같이 회원 정보를 입력 받아 출력하라!
이름? 홍길동
이름은 홍길동입니다.
*/
package bitcamp.pms;

import java.util.Scanner;

public class ProjectApp {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    System.out.print("이름? ");
    String input = keyScan.nextLine();
    System.out.printf("이름은 %s입니다.\n", input);
  }
}
