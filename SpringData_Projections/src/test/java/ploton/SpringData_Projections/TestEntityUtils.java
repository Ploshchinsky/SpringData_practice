package ploton.SpringData_Projections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ploton.SpringData_Projections.entity.Department;
import ploton.SpringData_Projections.entity.Employee;

class TestEntityUtils {
    Employee employee;
    Department department;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Ivan");
        employee.setLastName("Ivanov");
        employee.setPosition("Programmer");
        employee.setSalary(50000.00);
        employee.setDepartment(null);

        department = new Department();
        department.setId(1);
        department.setName("Global Department");
        department.setEmployees(null);
    }

    @Test
    void entitySerializationTest_CorrectEntity_CorrectJson() throws Exception {
        String expectedEmployee = "{\"id\":1,\"firstName\":\"Ivan\",\"lastName\":\"Ivanov\"," +
                "\"position\":\"Programmer\",\"salary\":50000.0}";
        String expectedDepartment = "{\"id\":1,\"name\":\"Global Department\",\"employees\":null}";

        String actualDepartment = new ObjectMapper().writeValueAsString(department);
        String actualEmployee = new ObjectMapper().writeValueAsString(employee);

        System.out.println(actualEmployee);
        System.out.println(actualDepartment);
        Assertions.assertEquals(expectedEmployee, actualEmployee);
        Assertions.assertEquals(expectedDepartment, actualDepartment);
    }
}
