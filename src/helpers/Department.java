package helpers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import global_data.GlobalTestData;
import utils.ListUtils.GenericListToMap;

public class Department {
  
  private Manager boss;            
  private Map<Integer, Employee> employees;
  
  public Department() {
    GenericListToMap<Integer, Employee> mapper = new GenericListToMap<Integer, Employee>();
    employees =  mapper.getMapFromList(GlobalTestData.getListOfEmployees());
  }
  
  public Optional<Manager> getBoss() {          
    return Optional.ofNullable(boss);    
  }    
  
  public void setBoss(Manager boss) {
    this.boss = boss;    
  }
  
  public Optional<Employee> findEmployeeById(int id){
    return Optional.ofNullable(employees.get(id));
  }
  
  /**
   * Get a list of employees for the given list of employee IDs.
   *  1. Uses a filter on the optional to get a stream of employees.
   *  2. We can then use get on the optional stream because we know there will be a value.
   */
  public List<Employee> findEmployeesByID_1(List<Integer> empIds){
    return empIds.stream()
        .map(this::findEmployeeById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  /**
   * Get a list of employees for the given list of employee IDs.
   *  1. Use flat map on the optional stream create by findEmployeeById.
   *  2. Then apply map to this stream. If it contains a value it is retrieved using orElseGet.
   */
  public List<Employee> findEmployeesByID_2(List<Integer> empIds){
    return empIds.stream()
        .map(this::findEmployeeById)
        .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty))
        .collect(Collectors.toList());
  }
}
