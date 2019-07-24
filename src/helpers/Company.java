package helpers;

import java.util.Optional;

public class Company {

  private Department department;
  
  public Optional<Department> getDepartment(){
    return Optional.ofNullable(department);
  }
  
  public void setDepartment(Department d) {
    this.department = d;
  }
}
