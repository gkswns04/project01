package bitcamp.pms.controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.TaskDao;
import bitcamp.pms.domain.Task;
import bitcamp.pms.util.CommandUtil;

@Controller
@RequestMapping("task/")
public class TaskController {
  private TaskDao taskDao;
  
  public void setTaskDao(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @RequestMapping("add.do")
  public void add(Scanner keyScan) {
    try {
      Task task = new Task();

      System.out.print("이메일? ");
      task.setWorkerEmail(keyScan.nextLine());
      System.out.print("작업명? ");
      task.setTitle(keyScan.nextLine());     
      System.out.print("시작일? ");
      task.setStartDate(Date.valueOf(keyScan.nextLine()));
      System.out.print("종료일? ");
      task.setEndDate(Date.valueOf(keyScan.nextLine()));
      

      if (CommandUtil.confirm(keyScan, "저장하시겠습니까?")) {
        taskDao.insert(task);
        System.out.println("저장하였습니다.");
      } else {
        System.out.println("저장을 취소하였습니다.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }
  
  @RequestMapping("delete.do")
  public void delete(Scanner keyScan) {
    try {
      System.out.print("삭제할 작업 번호?");
      int no = Integer.parseInt(keyScan.nextLine());

      if (CommandUtil.confirm(keyScan, "정말 삭제하시겠습니까?")) {
        int count = taskDao.delete(no);
        if (count > 0) {
          System.out.println("삭제하였습니다.");
        } else {
          System.out.println("유효하지 않은 번호이거나, 이미 삭제된 항목입니다.");
        }
      } else {
        System.out.println("삭제를 취소하였습니다.");
      }
      
    } catch (Exception e) {
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }
  
  @RequestMapping("list.do")
  public void list() {
    try {
      List<Task> tasks = taskDao.selectList();
      for (Task task : tasks) {
        System.out.println(task); 
            
      }
    } catch (Exception e) {
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }
  
  @RequestMapping("update.do")
  public void update(Scanner keyScan) {
    try {
      System.out.print("변경할 작업 번호?");
      int no = Integer.parseInt(keyScan.nextLine());

      Task task = taskDao.selectOne(no);
      if (task == null) {
        System.out.println("유효하지 않은 번호입니다.");
        return;
      }

      System.out.print("이메일? ");
      task.setWorkerEmail(keyScan.nextLine());
      System.out.print("작업명? ");
      task.setTitle(keyScan.nextLine());  
      System.out.print("내용? ");
      task.setContent(keyScan.nextLine());     
      System.out.print("시작일? ");
      task.setStartDate(Date.valueOf(keyScan.nextLine()));
      System.out.print("종료일? ");
      task.setEndDate(Date.valueOf(keyScan.nextLine()));

      if (CommandUtil.confirm(keyScan, "변경하시겠습니까?")) {
        int count = taskDao.update(task);
        if (count > 0) {
          System.out.println("변경하였습니다.");
        } else {
          System.out.println("유효하지 않은 번호이거나, 이미 삭제된 항목입니다.");
        }
      } else {
        System.out.println("변경을 취소하였습니다.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("데이터 로딩에 실패했습니다.");
    }
  }
}
