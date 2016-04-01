package bitcamp.pms.controller;

import java.util.List;
import java.util.Map;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Controller
public class MemberListController {
  private MemberDao memberDao;

  public void setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @RequestMapping("member/list.do")
  public void list(Map<String,Object> paramMap) {
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
}
