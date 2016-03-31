package bitcamp.pms.controller;

import java.util.List;
import java.util.Map;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.dao.ProjectDao;
import bitcamp.pms.domain.Project;

@Component("project/list.do")
public class ProjectListController implements MenuController {
  private ProjectDao projectDao;
  
  public void setProjectDao(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }
  
  @Override
  public void init() {}

  @Override
  public void service(Map<String,Object> paramMap) {
    try {
      List<Project> projects = projectDao.selectList();
      for (int i = 0; i < projects.size(); i++) {
        System.out.printf("%d, %s\n", i, projects.get(i).toString());
      }
    } catch (Exception e) {
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }

  @Override
  public void destroy() {}
}
