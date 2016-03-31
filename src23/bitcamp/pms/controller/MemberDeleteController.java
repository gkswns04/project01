package bitcamp.pms.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Member;

@Component("member/delete.do")
public class MemberDeleteController implements MenuController {
  private static final String filename = "member.data";
  private Scanner keyScan;

  public List<Member> load() throws Exception {
    ArrayList<Member> members = new ArrayList<>();
    
    FileReader in0 = new FileReader(filename);
    BufferedReader in = new BufferedReader(in0);

    String line;
    String[] values;
    Member member;
    while ((line = in.readLine()) != null) {
      values = line.split(",");
      member = new Member(values[0], values[1], values[2], values[3]);
      members.add(member);
    }

    in.close();
    in0.close();
    
    return members;
  }

  public void save(List<Member> members) throws Exception {
    FileWriter out0 = new FileWriter(filename);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    for (Member member : members) {
      out.println(member);
    }

    out.close();
    out1.close();
    out0.close();
  }

  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");

    try {
      List<Member> members = this.load();
      
      System.out.print("삭제할 회원 번호는? ");
      int no = Integer.parseInt(keyScan.nextLine());
  
      if (confirm("정말 삭제하시겠습니까?", true)) {
        members.remove(no);
        System.out.println("삭제하였습니다.");
      } else {
        System.out.println("삭제를 취소하였습니다.");
      }
      
      this.save(members);
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
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
