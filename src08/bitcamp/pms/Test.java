package bitcamp.pms;

import java.util.Scanner;

public class Test {
  static boolean confirm(String message) {
    Scanner keyScan = new Scanner(System.in);
    String input = null;
    do {
      System.out.printf("%s(y/n) ", message);
      input = keyScan.nextLine().toLowerCase();

      if (input.equals("y")) {
        return true;
      } else if (input.equals("n")) {
        return false;
      } else {
        System.out.println("잘못된 명령어입니다.");
      }
    } while(true);
  }

  public static void main(String[] args) {
    System.out.println(confirm("저장하시겠습니까?"));
  }
}
