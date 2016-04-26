package test;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {

  public static void main(String[] args) {
    Scanner keyScan = new Scanner(System.in);
    
    String password = null;
    String[] regexList = {"[0-9]", "[a-zA-Z_]", "[?!@]"};
    Pattern[] patternList = new Pattern[regexList.length];
    
    // 정규표현식 개수만큼 패턴 객체 준비 
    for (int i = 0; i < regexList.length; i++) { 
      patternList[i] = Pattern.compile(regexList[i]);
    }
    
    boolean isValid = true;
    
    while (true) {
      System.out.print("암호: ");
      password = keyScan.nextLine();
      
      // 암호가 세 개의 패턴과 일치하는지 분석한다.
      //isValid = true;
      for (Pattern pattern : patternList) {
        if (!pattern.matcher(password).find()) {
          isValid = false;
          break;
        }
      }
      
      if (isValid) {
        break;
      }
      
      System.out.println("암호는 4 ~ 10자 까지만 가능합니다.\n"
          + "최소 알파벳1개, 숫자1개, 특수문자(?,!,@)1개 반드시 포함.");
    }
    
    System.out.println("암호OK!");
    
    keyScan.close();
  }

}










