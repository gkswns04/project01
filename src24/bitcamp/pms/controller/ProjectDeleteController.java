package bitcamp.pms.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.dao.ProjectDao;
import bitcamp.pms.domain.Project;
import bitcamp.pms.util.CommandUtil;

@Component("project/delete.do")
public class ProjectDeleteController implements MenuController {
  ProjectDao projectDao = new ProjectDao();
  private Scanner keyScan;
  
  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    keyScan = (Scanner)paramMap.get("stdin");
    
    try {
      List<Project> projects = projectDao.load();
      
      System.out.print("삭제할 프로젝트 번호?");
      int no = Integer.parseInt(keyScan.nextLine());

      if (CommandUtil.confirm(keyScan, "정말 삭제하시겠습니까?")) {
        projects.remove(no);
        System.out.println("삭제하였습니다.");
      } else {
        System.out.println("삭제를 취소하였습니다.");
      }
      
      projectDao.save(projects);
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }

  @Override
  public void destroy() {}
 
}
