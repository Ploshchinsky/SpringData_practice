package ploton.SpringData_Projections.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ploton.SpringData_Projections.entity.Department;
import ploton.SpringData_Projections.entity.Employee;
import ploton.SpringData_Projections.entity.EmployeeProjection;
import ploton.SpringData_Projections.repository.EmployeeRepository;
import ploton.SpringData_Projections.service.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/")
    public ResponseEntity<Employee> save(@RequestBody Employee entity) {
        return new ResponseEntity<>(employeeService.save(entity), HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Employee>> findAll() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/projection/{id}")
    public ResponseEntity<EmployeeProjection> findProjectionById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(employeeService.findProjectionById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateById(@PathVariable("id") Integer id,
                                               @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(employeeService.updateById(id, updates), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(employeeService.deleteById(id), HttpStatus.OK);
    }
}
