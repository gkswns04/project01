/* Responsibility(or Role)
 * => 객체를 준비하고 관리한다. --> "빈 컨테이너(bean container)"라고 부른다.
 * => 객체(인스턴스) --> 자바에서는 "bean" 이라고 부른다.
 * => 관리하는 클래스를 보통 "컨테이너(container)"라고 부른다.
 */
package bitcamp.pms.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ApplicationContext {
  HashMap<String,Object> objPool = new HashMap<>();
  
  public ApplicationContext(String basePackage) {
    String path = "./bin/" + basePackage.replace(".", "/");
    createObject(new File(path));
  }
  
  private void createObject(File file) {
    if (file.isFile() && file.getName().endsWith(".class")) {
      String classNameWithPackage = file.getPath()  
                        .replace("./bin/", "") 
                        .replace(".class","")
                        .replace("/", "."); 
      try {
        Class<?> clazz = Class.forName(classNameWithPackage);
        if (!clazz.isInterface()) {
          Object obj = clazz.newInstance();
          objPool.put(obj.toString(), obj);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    } 
    
    File[] subfiles = file.listFiles();
    for (File subfile : subfiles) {
      createObject(subfile);
    }
  }
  
  public List<Object> getBeans(Class<?> beanType) {
    ArrayList<Object> list = new ArrayList<>();
    
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      if (beanType.isInstance(obj)) {
        list.add(obj);
      }
    }
    
    return list;
  }
  
  public Object getBean(String name) {
    return objPool.get(name);
  }
  
}











