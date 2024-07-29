package ploton.SpringData_Projections.service;

import org.springframework.stereotype.Service;
import ploton.SpringData_Projections.entity.Department;
import ploton.SpringData_Projections.repository.DepartmentRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department save(Department entity) {
        EntityUtils.validation(entity);
        return departmentRepository.save(entity);
    }

    @Override
    public List<Department> findAll() {
        return (List<Department>) departmentRepository.findAll();
    }

    @Override
    public Department findById(Integer id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.orElseThrow(() -> new NoSuchElementException("Department ID - " + id));
    }

    @Override
    public Department updateById(Integer id, Map<String, Object> updates) {
        Optional<Department> department = departmentRepository.findById(id);
        return department
                .map(dep -> EntityUtils.updateEntity(dep, updates))
                .map(departmentRepository::save)
                .orElseThrow(() -> new NoSuchElementException("Department ID - " + id));
    }

    @Override
    public Integer deleteById(Integer id) {
        if (!departmentRepository.existsById(id)) {
            throw new NoSuchElementException("Department ID - " + id);
        } else {
            departmentRepository.deleteById(id);
            return id;
        }
    }
}
