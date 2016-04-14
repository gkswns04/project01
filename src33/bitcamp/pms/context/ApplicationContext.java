/* 용도
=> @Component 및 @Controller 가 붙은 클래스에 대해서만 
   인스턴스를 생성하고 관리한다.
*/
package bitcamp.pms.context;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.annotation.Controller;

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
      
      if (!clazz.isAnnotationPresent(Component.class) && 
          !clazz.isAnnotationPresent(Controller.class)) {
        continue;
      }
      
      methods = clazz.getMethods();
      for (Method m : methods) {
        if (!m.getName().startsWith("set")) 
          continue;
        
        paramType = m.getParameterTypes()[0];
        dependency = findObjectByType(paramType);
        
        if (dependency == null) 
          continue;

        try {
          m.invoke(obj, dependency); 
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
        
        if (clazz.getAnnotation(Component.class) != null) {
          processComponentAnnotation(clazz);
          
        } else if (clazz.getAnnotation(Controller.class) != null) {
          processControllerAnnotation(clazz);
        }  
        
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    } 
    
    if (!file.isDirectory()) // .class 이나 디렉토리가 아닌 경우,
      return;
    
    File[] subfiles = file.listFiles();
    for (File subfile : subfiles) {
      createObject(subfile);
    }
  }
  
  private void processComponentAnnotation(Class<?> clazz) throws Exception {
    Component anno = clazz.getAnnotation(Component.class);
    String key = anno.value();
    if (key.equals("")) {
      key = clazz.getName();
    }
    objPool.put(key, clazz.newInstance());
  }
  
  private void processControllerAnnotation(Class<?> clazz) throws Exception {
    Controller anno = clazz.getAnnotation(Controller.class);
    String key = anno.value();
    if (key.equals("")) {
      key = clazz.getName();
    }
    objPool.put(key, clazz.newInstance());
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

  public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annoType) {
    Set<Entry<String,Object>> entrySet = objPool.entrySet();
    HashMap<String, Object> objMap = new HashMap<>();
    
    Object obj = null;
    for (Entry<String,Object> entry : entrySet) {
      obj = entry.getValue();
      
      if (obj.getClass().getAnnotation(annoType) == null)
        continue;
      
      objMap.put(entry.getKey(), obj);
    }
    
    return objMap;
  }

  public Object getBean(Class<?> type) {
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }
  
  public void addBean(String name, Object bean) {
    objPool.put(name, bean);
    injectDependency();
  }
  
}











