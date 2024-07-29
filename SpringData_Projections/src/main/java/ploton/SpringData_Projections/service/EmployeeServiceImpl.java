package ploton.SpringData_Projections.service;

import org.springframework.stereotype.Service;
import ploton.SpringData_Projections.entity.Employee;
import ploton.SpringData_Projections.entity.EmployeeProjection;
import ploton.SpringData_Projections.repository.EmployeeRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee entity) {
        EntityUtils.validation(entity);
        return employeeRepository.save(entity);
    }

    @Override
    public List<Employee> findAll() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Employee findById(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElseThrow(() -> new NoSuchElementException("Employee ID - " + id));
    }

    @Override
    public EmployeeProjection findProjectionById(Integer id) {
        Optional<EmployeeProjection> employeeProjection = employeeRepository.findProjectionById(id);
        return employeeProjection.orElseThrow(() -> new NoSuchElementException("Employee ID - " + id));
    }

    @Override
    public Employee updateById(Integer id, Map<String, Object> updates) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee
                .map(emp -> EntityUtils.updateEntity(emp, updates))
                .orElseThrow(() -> new NoSuchElementException("Employee ID - " + id));
    }

    @Override
    public Integer deleteById(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new NoSuchElementException("Employee ID - " + id);
        } else {
            employeeRepository.deleteById(id);
            return id;
        }
    }
}
