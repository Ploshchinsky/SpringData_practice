package ploton.SpringData_Projections.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ploton.SpringData_Projections.entity.Employee;
import ploton.SpringData_Projections.entity.EmployeeProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository
        extends CrudRepository<Employee, Integer>, PagingAndSortingRepository<Employee, Integer> {
    Optional<EmployeeProjection> findProjectionById(Integer id);
}
