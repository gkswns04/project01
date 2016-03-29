// @Component가 붙은 클래스에 대해서만 인스턴스를 생성하고 관리한다.
package bitcamp.pms.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import bitcamp.pms.annotation.Component;

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
        
        // @Component 애노테이션이 붙었는지 알아본다.
        Component anno = clazz.getAnnotation(Component.class);
        
        if (anno != null) { // @Component 애노테이션이 있다면 
          Object obj = clazz.newInstance(); // 해당 클래스의 인스턴스를 생성한다.
          objPool.put(anno.value(), obj); // 애노테이션의 value 값을 이용하여 인스턴스를 저장한다.
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











