package pl.carsrental.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long employeeId) {
        isEmployeeExists(employeeId);
        return employeeRepository.getById(employeeId);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
        log.info("Saved " + employee);
    }

    public void deleteEmployeeById(Long employeeId) {
        isEmployeeExists(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    public void employeeChangeStanding(Long employeeId) {

        isEmployeeExists(employeeId);

        Employee employee = employeeRepository.getById(employeeId);
        Stand employeeStanding = employee.getStanding();

        if (employeeStanding == Stand.MANAGER) {
            employee.setStanding(Stand.EMPLOYEE);
            log.info("Employee with id: " + employee.getId() + " demoted");
        } else if (employeeStanding == Stand.EMPLOYEE) {
            employee.setStanding(Stand.MANAGER);
            log.info("Employee with id: " + employee.getId() + " promoted");
        } else {
            log.error("Wrong standing");
        }
    }

    private void isEmployeeExists(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if (!exists) {
            throw new IllegalStateException("employee with id " + employeeId + " does not exists");
        }
    }

}
