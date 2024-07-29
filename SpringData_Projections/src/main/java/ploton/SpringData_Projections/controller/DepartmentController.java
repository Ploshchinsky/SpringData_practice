package ploton.SpringData_Projections.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ploton.SpringData_Projections.entity.Department;
import ploton.SpringData_Projections.service.DepartmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/")
    public ResponseEntity<Department> save(@RequestBody Department entity) {
        return new ResponseEntity<>(departmentService.save(entity), HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Department>> findAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Department> updateById(@PathVariable("id") Integer id,
                                                 @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(departmentService.updateById(id, updates), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(departmentService.deleteById(id), HttpStatus.OK);
    }
}
