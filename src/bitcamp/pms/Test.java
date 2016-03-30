package bitcamp.pms;

import java.util.Scanner;
import java.util.ArrayList;

public class Test {
  static ArrayList<String> studentList1 = new ArrayList<>();
  static ArrayList<String> studentList2 = new ArrayList<>();
  
  public static void main(String[] args) throws Exception {
    String[] students1 = {
      "송지환", "명재환", "이민섭", "김평일", "강성준", "김한준",
      "신기학", "곽용호", "최한비", "배윤호", "권승협"
    };

    String[] students2 = {
      "이대희", "송석원", "최춘호", "박기석", "민경훈", "이민우",
      "이천배", "김양모", "김운남", "박현신", "김현지"
    };

    
    for (int i = 0; i < students1.length; i++) {
      studentList1.add(students1[i]);
      studentList2.add(students2[i]);
    }


    /*
    String[] students2 = {"이대희", "송석원", "최춘호", "송지환",
      "곽용호", "박기석", "권승협", "강성준",
      "김평일", "신기학", "민경훈",1
      "최한비", "이민우", "이민섭", "김동래",
      "명재환", "김양모", "김운남", "박현신",
      "배윤호", "김현지", "김한준"
    };
    */

    Scanner keyScan = new Scanner(System.in);
    
    while (studentList1.size() > 0) {
      keyScan.nextLine();
      System.out.printf("%s <--> ", selectStudent(studentList1));
      keyScan.nextLine();
      System.out.println(selectStudent(studentList2));
    }

  }
  
  static String selectStudent(ArrayList<String> list) {
    return list.remove((int)(Math.random() * list.size()));
  }
}
