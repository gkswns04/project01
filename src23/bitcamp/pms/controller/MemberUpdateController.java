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

@Component("member/update.do")
public class MemberUpdateController implements MenuController {
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
      
      System.out.print("변경할 회원 번호는? ");
      int no = Integer.parseInt(keyScan.nextLine());
  
      Member oldMember = members.get(no);
      Member member = new Member();
  
      System.out.printf("이름(%s)? ", oldMember.getName());
      member.setName(keyScan.nextLine());
  
      System.out.printf("이메일(%s)? ", oldMember.getEmail());
      member.setEmail(keyScan.nextLine());
  
      System.out.printf("암호(%s)? ", oldMember.getPassword());
      member.setPassword(keyScan.nextLine());
  
      System.out.printf("전화(%s)? ", oldMember.getTel());
      member.setTel(keyScan.nextLine());
  
      if (confirm("변경하시겠습니까?", true)) {
        members.set(no, member);
        System.out.println("변경하였습니다.");
      } else {
        System.out.println("변경을 취소하였습니다.");
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
