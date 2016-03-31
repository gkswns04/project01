package bitcamp.pms.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Member;

@Component("member/list.do")
public class MemberListController implements MenuController {
  private static final String filename = "member.data";

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

  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    try {
      List<Member> members = this.load();
      
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
