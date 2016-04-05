package bitcamp.pms.controller;

import java.util.List;
import java.util.Scanner;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;
import bitcamp.pms.util.CommandUtil;

@Controller
@RequestMapping("member/")
public class MemberController {
  private MemberDao memberDao;

  public void setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @RequestMapping("add.do")
  public void add(Scanner keyScan) {
    Member member = new Member();

    System.out.print("이름? ");
    member.setName(keyScan.nextLine());

    System.out.print("이메일? ");
    member.setEmail(keyScan.nextLine());

    System.out.print("암호? ");
    member.setPassword(keyScan.nextLine());

    System.out.print("전화? ");
    member.setTel(keyScan.nextLine());

    if (CommandUtil.confirm(keyScan, "저장하시겠습니까?")) {
      try {
        memberDao.insert(member);
        System.out.println("저장하였습니다.");
      } catch (Exception e) {
        System.out.println("데이터를 저장하는데 실패했습니다.");
      }
    } else {
      System.out.println("저장을 취소하였습니다.");
    }
  }
  
  @RequestMapping("delete.do")
  public void delete(Scanner keyScan) {
    try {
      System.out.print("삭제할 회원 번호는? ");
      int no = Integer.parseInt(keyScan.nextLine());
  
      if (CommandUtil.confirm(keyScan, "정말 삭제하시겠습니까?")) {
        memberDao.delete(no);
        System.out.println("삭제하였습니다.");
      } else {
        System.out.println("삭제를 취소하였습니다.");
      }
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
    }
  }
  
  @RequestMapping("list.do")
  public void list() {
    try {
      List<Member> members = memberDao.selectList();
      
      Member member = null;
      for (int i = 0; i < members.size(); i++) {
        member = members.get(i);
        System.out.printf("%d, %s\n", i, member.toString());
      }
    } catch (Exception e) {
      throw new RuntimeException("회원 데이터 로딩 실패!", e);
    }
  }
  
  @RequestMapping("update.do")
  public void update(Scanner keyScan) {
    try {
      System.out.print("변경할 회원 번호는? ");
      int no = Integer.parseInt(keyScan.nextLine());
  
      Member oldMember = memberDao.selectOne(no);
      Member member = new Member();
  
      System.out.printf("이름(%s)? ", oldMember.getName());
      member.setName(keyScan.nextLine());
  
      System.out.printf("이메일(%s)? ", oldMember.getEmail());
      member.setEmail(keyScan.nextLine());
  
      System.out.printf("암호(%s)? ", oldMember.getPassword());
      member.setPassword(keyScan.nextLine());
  
      System.out.printf("전화(%s)? ", oldMember.getTel());
      member.setTel(keyScan.nextLine());
  
      if (CommandUtil.confirm(keyScan, "변경하시겠습니까?")) {
        memberDao.update(no, member);
        System.out.println("변경하였습니다.");
      } else {
        System.out.println("변경을 취소하였습니다.");
      }
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
    }
  }
}
