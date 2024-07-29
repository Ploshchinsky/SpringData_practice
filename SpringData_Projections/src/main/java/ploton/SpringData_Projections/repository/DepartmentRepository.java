package ploton.SpringData_Projections.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ploton.SpringData_Projections.entity.Department;

@Repository
public interface DepartmentRepository
        extends CrudRepository<Department, Integer>, PagingAndSortingRepository<Department, Integer> {
}
