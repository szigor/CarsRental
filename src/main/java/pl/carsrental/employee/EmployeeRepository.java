package pl.carsrental.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> getEmployees();

    Employee getEmployeeById(Long employeeId);

    void deleteEmployeeById(Long employeeId);

    void employeeChangeStanding(Long employeeId);
}
