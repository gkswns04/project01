package bitcamp.pms.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import bitcamp.pms.domain.Project;

public class ProjectDao {
  private static final String filename = "project.data";
  
  public List<Project> load() throws Exception {
    ArrayList<Project> projects = new ArrayList<>();
    
    FileReader in0 = new FileReader(filename);
    BufferedReader in = new BufferedReader(in0);

    String line;
    String[] values;
    Project project;
    while ((line = in.readLine()) != null) {
      values = line.split(",");
      project = new Project(values[0],
                            Date.valueOf(values[1]),
                            Date.valueOf(values[2]));
      project.setState(Integer.parseInt(values[3]));
      project.setDescription(values[4]);
      projects.add(project);
    }

    in.close();
    in0.close();
    
    return projects;
  }
  
  public void insert(Project project) throws Exception {
    FileWriter out0 = new FileWriter(filename, true);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    out.println(project);

    out.close();
    out1.close();
    out0.close();
  }
  
  public void save(List<Project> projects) throws Exception {
    FileWriter out0 = new FileWriter(filename);
    BufferedWriter out1 = new BufferedWriter(out0);
    PrintWriter out = new PrintWriter(out1);

    for (Project project : projects) {
      out.println(project);
    }

    out.close();
    out1.close();
    out0.close();
  }
}
