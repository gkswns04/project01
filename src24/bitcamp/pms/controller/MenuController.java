// ProjectApp과 메뉴 처리기 사이의 호출 규칙
// => ProjectApp은 메뉴를 처리할 때 이 규칙에서 정의한 메서드를 호출한다.
//
package bitcamp.pms.controller;

import java.util.Map;

public interface MenuController {
  void init();
  void service(Map<String,Object> paramMap);
  void destroy();
}
