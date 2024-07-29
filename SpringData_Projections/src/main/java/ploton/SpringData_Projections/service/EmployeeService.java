package ploton.SpringData_Projections.service;

import ploton.SpringData_Projections.entity.Employee;
import ploton.SpringData_Projections.entity.EmployeeProjection;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee save(Employee entity);

    List<Employee> findAll();

    Employee findById(Integer id);

    EmployeeProjection findProjectionById(Integer id);

    Employee updateById(Integer id, Map<String, Object> updates);

    Integer deleteById(Integer id);
}
