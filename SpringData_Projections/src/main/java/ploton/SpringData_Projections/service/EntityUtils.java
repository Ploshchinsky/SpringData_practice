package ploton.SpringData_Projections.service;

import ploton.SpringData_Projections.entity.Department;
import ploton.SpringData_Projections.entity.Employee;
import ploton.SpringData_Projections.exception.ValidateEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityUtils {
    public static <T> void validation(T entity) {
        if (entity instanceof Department) {
            departmentValidation((Department) entity);
        } else if (entity instanceof Employee) {
            employeeValidation((Employee) entity);
        }
    }

    public static <T> T updateEntity(T entity, Map<String, Object> updates) {
        if (entity instanceof Department) {
            return (T) departmentUpdate((Department) entity, updates);
        } else if (entity instanceof Employee) {
            return (T) employeeUpdate((Employee) entity, updates);
        }
        return null;
    }

    private static Employee employeeUpdate(Employee entity, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            switch (key) {
                case "id":
                    if (value instanceof Integer)
                        entity.setId((Integer) value);
                    break;
                case "firstName":
                    if (value instanceof String)
                        entity.setFirstName((String) value);
                    break;
                case "lastName":
                    if (value instanceof String)
                        entity.setLastName((String) value);
                    break;
                case "position":
                    if (value instanceof String)
                        entity.setPosition((String) value);
                    break;
                case "salary":
                    if (value instanceof Double)
                        entity.setSalary((Double) value);
                    break;
                case "department":
                    if (value instanceof Department)
                        entity.setDepartment((Department) value);
                    break;
                default:
                    throw new ValidateEntityException("Employee Update Error. Wrong field: " + key + " - " + value);
            }
        });
        return entity;
    }

    private static Department departmentUpdate(Department entity, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            switch (key) {
                case "id":
                    if (value instanceof Integer)
                        entity.setId((Integer) value);
                    break;
                case "name":
                    if (value instanceof String)
                        entity.setName((String) value);
                    break;
                case "employees":
                    if (value instanceof List)
                        entity.setEmployees((List<Employee>) value);
                    break;
                default:
                    throw new ValidateEntityException("Department Update Error. Wrong field: " + key + " - " + value);
            }
        });
        return entity;
    }

    private static void employeeValidation(Employee entity) {
        List<String> errors = new ArrayList<>();
        if (!entity.getId().toString().matches("^\\d+$")) {
            errors.add("Employee wrong argument. Filed ID - " + entity.getId());
        }
        if (!entity.getFirstName().matches("^[a-zA-Z ]+$")) {
            errors.add("Employee wrong argument. Filed FirstName - " + entity.getFirstName());
        }
        if (!entity.getLastName().matches("^[a-zA-Z ]+$")) {
            errors.add("Employee wrong argument. Filed LastName - " + entity.getFirstName());
        }
        if (!entity.getSalary().toString().matches("^\\d+\\.\\d+$")) {
            errors.add("Employee wrong argument. Filed Salary - " + entity.getSalary());
        }
        if (!entity.getPosition().matches("^[a-zA-Z ]+$")) {
            errors.add("Employee wrong argument. Filed Position - " + entity.getPosition());
        }
        if (!errors.isEmpty()) {
            throw new ValidateEntityException(errors.toString());
        }
    }

    private static void departmentValidation(Department entity) {
        List<String> errors = new ArrayList<>();
        if (!entity.getId().toString().matches("^\\d+$")) {
            errors.add("Department wrong argument. Filed ID - " + entity.getId());
        }
        if (!entity.getName().matches("^[a-zA-Z ]+$")) {
            errors.add("Department wrong argument. Filed Name - " + entity.getName());
        }
        if (!errors.isEmpty()) {
            throw new ValidateEntityException(errors.toString());
        }
    }
}
