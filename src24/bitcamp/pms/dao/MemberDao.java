package bitcamp.pms.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.domain.Member;

public class MemberDao {
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
  
  public void insert(Member member) throws Exception {
    FileWriter out0 = new FileWriter(filename, true);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    out.println(member);

    out.close();
    out1.close();
    out0.close();
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
}
