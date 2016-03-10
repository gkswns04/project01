/* 목표
- 명령어에 따라 회원 정보를 다룰 수 있도록 변경하라.
명령> add
이름? 홍길동
이메일? hong@test.com
암호? 1111
전화? 111-1111
저장하시겠습니까?(y/n) y
저장하였습니다.
저장하시겠습니까?(y/n) N
저장을 취소하였습니다.
명령> list
0, 홍길동, hong@test.com, 1111, 1111-2222
1, 홍길동, hong@test.com, 1111, 1111-2222
2, 홍길동, hong@test.com, 1111, 1111-2222
3, 홍길동, hong@test.com, 1111, 1111-2222
명령> delete
삭제할 회원의 번호는? 2
정말로 삭제하시겠습니까?(y/n) y
삭제하였습니다.
정말로 삭제하시겠습니까?(y/n) n
삭제를 취소하였습니다.
명령> list
0, 홍길동, hong@test.com, 1111, 1111-2222
1, 홍길동, hong@test.com, 1111, 1111-2222
2,
3, 홍길동, hong@test.com, 1111, 1111-2222
명령> update
변경할 회원 번호는? 1
이름(홍길동)? 임꺽정
이메일(hong@test.com)? leem@test.com
암호(1111)? 1112
전화(1111-2222)? 1111-1234
변경하시겠습니까?(y/n) y
변경하였습니다.
변경하시겠습니까?(y/n) n
변경을 취소하였습니다.
명령> list
0, 홍길동, hong@test.com, 1111, 1111-2222
1, 임꺽정, leem@test.com, 1112, 1111-1234
2,
3, 홍길동, hong@test.com, 1111, 1111-2222
명령> quit
안녕히 가세요!
명령> xxx
올바르지 않은 명령어입니다.
명령>
- 사용 문법:
  => 반복문과 조건문의 활용
*/
package bitcamp.pms;

import java.util.Scanner;
import bitcamp.pms.domain.Member;

public class ProjectApp {
  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);

    Member[] members = new Member[1000];
    Member member = null;
    int count = 0;
    String input;
    int no = 0;

    while (true) {
      System.out.print("명령> ");
      input = keyScan.nextLine();
      if (input.equals("quit")) {
        System.out.println("안녕히 가세요!");
        break;
      } else if (input.equals("add")) {
        member = new Member();

        System.out.print("이름? ");
        member.setName(keyScan.nextLine());

        System.out.print("이메일? ");
        member.setEmail(keyScan.nextLine());

        System.out.print("암호? ");
        member.setPassword(keyScan.nextLine());

        System.out.print("전화? ");
        member.setTel(keyScan.nextLine());

        if (confirm("저장하시겠습니까?", true)) {
          members[count++] = member;
          System.out.println("저장하였습니다.");
        } else {
          System.out.println("저장을 취소하였습니다.");
        }
      } else if (input.equals("update")) {
        System.out.print("변경할 회원 번호는? ");
        no = Integer.parseInt(keyScan.nextLine());

        member = new Member();

        System.out.printf("이름(%s)? ", members[no].getName());
        member.setName(keyScan.nextLine());

        System.out.printf("이메일(%s)? ", members[no].getEmail());
        member.setEmail(keyScan.nextLine());

        System.out.printf("암호(%s)? ", members[no].getPassword());
        member.setPassword(keyScan.nextLine());

        System.out.printf("전화(%s)? ", members[no].getTel());
        member.setTel(keyScan.nextLine());

        if (confirm("변경하시겠습니까?", true)) {
          members[no] = member;
          System.out.println("변경하였습니다.");
        } else {
          System.out.println("변경을 취소하였습니다.");
        }
      } else if (input.equals("list")) {
        for (int i = 0; i < count; i++) {
          System.out.printf("%d, %s\n", i,
            (members[i] != null)?members[i].toString():"");
        }

      } else if (input.equals("delete")) {
        System.out.print("삭제할 회원 번호는? ");
        no = Integer.parseInt(keyScan.nextLine());
        if (confirm("정말 삭제하시겠습니까?", true)) {
          members[no] = null;
          for (int i = no + 1; i < count; i++) {
            members[i-1] = members[i];
          }
          count--;
          System.out.println("삭제하였습니다.");
        } else {
          System.out.println("삭제를 취소하였습니다.");
        }

      } else {
        System.out.println("올바르지 않은 명령어입니다.");
      }
    }
  }

  static boolean confirm(String message, boolean strictMode) {
    Scanner keyScan = new Scanner(System.in);
    String input = null;
    do {
      System.out.printf("%s(y/n) ", message);
      input = keyScan.nextLine().toLowerCase();

      if (input.equals("y")) {
        return true;
      } else if (input.equals("n")) {
        return false;
      } else {
        if (!strictMode) {
          return false;
        }
        System.out.println("잘못된 명령어입니다.");
      }
    } while(true);
  }
}
