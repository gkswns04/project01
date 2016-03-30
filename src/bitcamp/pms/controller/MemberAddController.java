package bitcamp.pms.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Member;

@Component("member/add.do")
public class MemberAddController implements MenuController {
  private static final String filename = "member.data";
  private Scanner keyScan;

  public void insert(Member member) throws Exception {
    FileWriter out0 = new FileWriter(filename, true);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    out.println(member);

    out.close();
    out1.close();
    out0.close();
  }

  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");

    Member member = new Member();

    System.out.print("이름? ");
    member.setName(keyScan.nextLine());

    System.out.print("이메일? ");
    member.setEmail(keyScan.nextLine());

    System.out.print("암호? ");
    member.setPassword(keyScan.nextLine());

    System.out.print("전화? ");
    member.setTel(keyScan.nextLine());

    if (confirm("저장하시겠습니까?", true)) {
      try {
        this.insert(member);
        System.out.println("저장하였습니다.");
      } catch (Exception e) {
        System.out.println("데이터를 저장하는데 실패했습니다.");
      }
    } else {
      System.out.println("저장을 취소하였습니다.");
    }
  }

  @Override
  public void destroy() {}

  private boolean confirm(String message, boolean strictMode) {
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
