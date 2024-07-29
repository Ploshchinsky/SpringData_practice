package ploton.SpringData_Projections.entity;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeProjection {
    @Value("#{target.firstName + ' ' + target.lastName}")
    public String getFullName();

    @Value("#{target.position}")
    public String getPosition();

    @Value("#{target.department.name}")
    public String getDepartmentName();
}
