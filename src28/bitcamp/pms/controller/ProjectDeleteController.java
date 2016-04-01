package bitcamp.pms.controller;

import java.util.Scanner;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.ProjectDao;
import bitcamp.pms.util.CommandUtil;

@Controller
public class ProjectDeleteController {
  private ProjectDao projectDao;
  
  public void setProjectDao(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @RequestMapping("project/delete.do")
  public void delete(Scanner keyScan) {
    try {
      System.out.print("삭제할 프로젝트 번호?");
      int no = Integer.parseInt(keyScan.nextLine());

      if (CommandUtil.confirm(keyScan, "정말 삭제하시겠습니까?")) {
        projectDao.delete(no);
        System.out.println("삭제하였습니다.");
      } else {
        System.out.println("삭제를 취소하였습니다.");
      }
      
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효한 번호가 아닙니다.");
    } catch (Exception e) {
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }
}
