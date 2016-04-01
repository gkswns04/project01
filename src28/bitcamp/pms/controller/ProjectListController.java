package bitcamp.pms.controller;

import java.util.List;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.ProjectDao;
import bitcamp.pms.domain.Project;

@Controller
public class ProjectListController {
  private ProjectDao projectDao;
  
  public void setProjectDao(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @RequestMapping("project/list.do")
  public void list() {
    try {
      List<Project> projects = projectDao.selectList();
      for (int i = 0; i < projects.size(); i++) {
        System.out.printf("%d, %s\n", i, projects.get(i).toString());
      }
    } catch (Exception e) {
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }
}
