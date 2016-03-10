/* 목표
- 값 객체(Value Object)를 이용하여 입력 받은 회원정보를 저장하라.
  동작은 버전 03과 같다.
- 사용 문법:
  => Value Object(사용자 정의 데이터 타입) 클래스의 선언.
     예) Member 클래스  
*/
package bitcamp.pms;

import java.util.Scanner;
import bitcamp.pms.domain.Member;

public class ProjectApp {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    Member member = new Member();

    System.out.print("이름? ");
    member.setName(keyScan.nextLine());

    System.out.print("이메일? ");
    member.setEmail(keyScan.nextLine());

    System.out.print("암호? ");
    member.setPassword(keyScan.nextLine());

    System.out.print("전화? ");
    member.setTel(keyScan.nextLine());

    System.out.println("--------------------------------");
    System.out.println(member);
  }
}
