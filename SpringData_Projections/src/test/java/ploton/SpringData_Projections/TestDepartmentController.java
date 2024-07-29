package ploton.SpringData_Projections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ploton.SpringData_Projections.controller.DepartmentController;
import ploton.SpringData_Projections.entity.Department;
import ploton.SpringData_Projections.entity.Employee;
import ploton.SpringData_Projections.service.DepartmentService;
import ploton.SpringData_Projections.service.EmployeeService;

import java.util.List;
import java.util.NoSuchElementException;

@WebMvcTest(DepartmentController.class)
@ExtendWith(SpringExtension.class)
public class TestDepartmentController {
    @MockBean
    DepartmentService departmentService;
    @Autowired
    MockMvc mockMvc;
    Employee employee;
    Department department;
    String wrongJson;

    @BeforeEach
    void setUp() {
        wrongJson = "{\"id\": -1, \"name\": 500}";

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
    void departmentSave_CorrectInput_HttpAccepted() throws Exception {
        Mockito.when(departmentService.save(Mockito.any(Department.class))).thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(department)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(department.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(department.getName()));
    }

    @Test
    void employeeSave_IncorrectInput_HttpBadRequest() throws Exception {
        Mockito.when(departmentService.save(Mockito.any(Department.class))).thenThrow(new IllegalArgumentException(
                "Department Save Error"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongJson))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    void employeeFindById_CorrectId_HttpOK() throws Exception {
        Mockito.when(departmentService.findById(Mockito.anyInt())).thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/departments/1", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(department.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(department.getName()));
    }

    @Test
    void employeeFindById_IncorrectId_HttpNotFound() throws Exception {
        Mockito.when(departmentService.findById(Mockito.anyInt()))
                .thenThrow(new NoSuchElementException("Department ID - " + 1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/departments/1", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void employeeFindAll_Correct_HttpOK() throws Exception {
        Mockito.when(departmentService.findAll()).thenReturn(List.of(department));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/departments/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(department.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(department.getName()));
    }

    @Test
    void employeeDeleteById_CorrectId_HttpOK() throws Exception {
        Mockito.when(departmentService.deleteById(Mockito.anyInt())).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/departments/1", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    void employeeDeleteById_IncorrectId_HttpNotFound() throws Exception {
        Mockito.when(departmentService.deleteById(Mockito.anyInt())).thenThrow(new NoSuchElementException("" +
                "Department ID - 1"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/departments/1", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void employeeUpdateById_CorrectInput_HttpOK() throws Exception {
        Department updatedDepartment = department;
        updatedDepartment.setName("Updated Name");

        Mockito.when(departmentService.updateById(Mockito.anyInt(), Mockito.anyMap()))
                .thenReturn(updatedDepartment);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/departments/1", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedDepartment.getName()));
    }

    @Test
    void employeeUpdatedById_IncorrectId_HttpNotFound() throws Exception {
        Mockito.when(departmentService.updateById(Mockito.anyInt(), Mockito.anyMap()))
                .thenThrow(new NoSuchElementException("Department ID - 1"));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Name\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}