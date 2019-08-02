package helpers;

import utils.ListUtils.IdAble;

public class Employee implements IdAble<Integer>, Comparable<Employee>{

  private String name;
  private String department;
  private int id;
  private int salary;

  public Employee() {
    this.id = -1;
    this.name = "name";
    this.department = "no dept";
    this.salary = 0;
  }
  
  public Employee(int id, String name, int salary, String department) {
    this.id = id;
    this.name = name;
    this.department = department;
    this.salary = salary;
  }
    
  @Override
  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
  
  public String getDepartment() {
    return department;
  }
  
  public int getSalary() {
    return salary;
  }

  @Override
  public String toString() {
    return "Employee [name=" + name + ", department=" + department + ", id=" + id + ", salary=" + salary + "]";
  }

  @Override
  public int compareTo(Employee o) {
    return (this.getSalary() > o.getSalary()) ? 0 : -1;
  }
}
