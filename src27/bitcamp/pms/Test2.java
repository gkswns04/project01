package bitcamp.pms;

import java.util.Scanner;
import java.util.ArrayList;

public class Test2 {
  public static void main(String[] args) throws Exception {
    String[] students = {"이대희", "송석원", "최춘호", "송지환",
      "곽용호", "박기석", "권승협", "강성준",
      "김평일", "신기학", "민경훈",
      "최한비", "이민우", "이민섭", "김동래",
      "명재환", "김양모", "김운남", "박현신",
      "배윤호", "김현지", "김한준"
    };

    Scanner keyScan = new Scanner(System.in);
    System.out.print("임의의 숫자를 입력하시오> ");
    int count = Integer.parseInt(keyScan.nextLine());
    for (int i = 0; i < count; i++) {
      Thread.sleep(200);
      System.out.print(".");
    }

    System.out.println(students[(int)(Math.random() * students.length)]);

  }
}
