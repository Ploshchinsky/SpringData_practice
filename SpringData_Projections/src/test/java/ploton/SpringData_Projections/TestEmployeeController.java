package ploton.SpringData_Projections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ploton.SpringData_Projections.controller.EmployeeController;
import ploton.SpringData_Projections.entity.Department;
import ploton.SpringData_Projections.entity.Employee;
import ploton.SpringData_Projections.service.DepartmentService;
import ploton.SpringData_Projections.service.EmployeeService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@WebMvcTest(EmployeeController.class)
@ExtendWith(SpringExtension.class)
public class TestEmployeeController {
    @MockBean
    EmployeeService employeeService;
    @Autowired
    MockMvc mockMvc;
    Employee employee;
    Department department;
    String wrongJson;

    @BeforeEach
    void setUp() {
        wrongJson = "{\"id\": -1, \"firstName\": 500, \"lastName\":  900, \"position\": 100, \"salary\": \"abc\"," +
                "\"department\": null}";

        department = new Department();
        department.setId(1);
        department.setName("Global Department");

        employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Ivan");
        employee.setLastName("Ivanov");
        employee.setPosition("Programmer");
        employee.setSalary(100000.00);
        employee.setDepartment(department);
    }

    @Test
    void employeeSave_CorrectInput_HttpAccepted() throws Exception {
        Mockito.when(employeeService.save(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(employee.getPosition()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    void employeeSave_IncorrectInput_HttpBadRequest() throws Exception {
        Mockito.when(employeeService.save(Mockito.any(Employee.class))).thenThrow(new IllegalArgumentException("" +
                "Employee Save Error"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void employeeFindById_CorrectId_HttpOK() throws Exception {
        Mockito.when(employeeService.findById(Mockito.anyInt())).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/1", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(employee.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(employee.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(employee.getPosition()));
    }

    @Test
    void employeeFindById_IncorrectId_HttpNotFound() throws Exception {
        Mockito.when(employeeService.findById(Mockito.anyInt()))
                .thenThrow(new NoSuchElementException("Employee ID - " + 1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/1", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void employeeFindAll_Correct_HttpOK() throws Exception {
        Mockito.when(employeeService.findAll()).thenReturn(List.of(employee));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(employee.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(employee.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].position").value(employee.getPosition()));
    }

    @Test
    void employeeDeleteById_CorrectId_HttpOK() throws Exception {
        Mockito.when(employeeService.deleteById(Mockito.anyInt())).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/1", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    void employeeDeleteById_IncorrectId_HttpNotFound() throws Exception {
        Mockito.when(employeeService.deleteById(Mockito.anyInt())).thenThrow(new NoSuchElementException("" +
                "Employee ID - 1"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/1", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void employeeUpdateById_CorrectInput_HttpOK() throws Exception {
        Employee updatedEmployee = employee;
        updatedEmployee.setFirstName("Updated FirstName");
        updatedEmployee.setSalary(666.666);

        Mockito.when(employeeService.updateById(Mockito.anyInt(), Mockito.anyMap()))
                .thenReturn(updatedEmployee);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/employees/1", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Updated FirstName\", \"salary\": 666.666}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(updatedEmployee.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(updatedEmployee.getSalary()));
    }

    @Test
    void employeeUpdatedById_IncorrectId_HttpNotFound() throws Exception {
        Mockito.when(employeeService.updateById(Mockito.anyInt(), Mockito.anyMap()))
                .thenThrow(new NoSuchElementException("Employee ID - 1"));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Updated FirstName\", \"salary\": 666.666}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
