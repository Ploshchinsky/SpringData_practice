package ploton.SpringData_Projections.service;

import ploton.SpringData_Projections.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department save(Department entity);

    List<Department> findAll();

    Department findById(Integer id);

    Department updateById(Integer id, Map<String, Object> updates);

    Integer deleteById(Integer id);
}
