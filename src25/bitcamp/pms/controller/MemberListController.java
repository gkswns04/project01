package bitcamp.pms.controller;

import java.util.List;
import java.util.Map;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;

@Component("member/list.do")
public class MemberListController implements MenuController {
  MemberDao memberDao = new MemberDao();
  
  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
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

  @Override
  public void destroy() {}

}
