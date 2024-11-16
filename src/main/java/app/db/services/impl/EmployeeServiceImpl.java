package app.db.services.impl;

import app.db.entities.Employee;
import app.db.reposotories.EmployeeRepository;
import app.db.services.interfaces.EmployeeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
@Getter
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Employee> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return (List<Employee>) repository.findAll();
    }

    @Override
    public void save(Employee employee) {
        if (employee != null) {
            repository.save(employee);
        }
    }

    @Override
    public void update(Employee employee) {
        if (employee != null && repository.existsById((long) Math.toIntExact(employee.getId()))) {
            repository.save(employee);
        }
    }

    @Override
    public void delete(Employee employee) {
        if (employee != null && repository.existsById((long) Math.toIntExact(employee.getId()))) {
            repository.deleteById((long) Math.toIntExact(employee.getId()));
        }
    }

    @Override
    public void deleteById(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}

