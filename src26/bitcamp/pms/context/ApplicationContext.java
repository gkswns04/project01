// @Component가 붙은 클래스에 대해서만 인스턴스를 생성하고 관리한다.
package bitcamp.pms.context;

import java.io.File;
import java.lang.reflect.Method;
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
    injectDependency();
  }
  
  private void injectDependency() {
    Collection<Object> objects = objPool.values();
    Class<?> clazz = null;
    Method[] methods = null;
    Class<?> paramType = null;
    Object dependency = null;
    for (Object obj : objects) {
      clazz = obj.getClass();
      //System.out.println(clazz.getName());
      methods = clazz.getMethods();
      for (Method m : methods) {
        if (!m.getName().startsWith("set")) 
          continue;
        
        paramType = m.getParameterTypes()[0];
        //System.out.printf("--->%s(%s)\n", m.getName(), paramType.getName());   
        dependency = findObjectByType(paramType);
        
        if (dependency == null) // 셋터가 원하는 의존 객체를 찾지 못하면, 
          continue;

        try {
          m.invoke(obj, dependency); // 셋터를 호출하여 의존 객체를 주입한다.
        } catch (Exception e) {}
      }
    }
    
  }

  private Object findObjectByType(Class<?> paramType) {
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      if (paramType.isInstance(obj)) {
        return obj;
      }
    }
    return null;
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
          if (anno.value().equals("")) {
            objPool.put(clazz.getName(), obj);
          } else {
            objPool.put(anno.value(), obj); // 애노테이션의 value 값을 이용하여 인스턴스를 저장한다.
          }
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











