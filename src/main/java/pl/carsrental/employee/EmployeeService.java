package pl.carsrental.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
        log.info("Added " + employee);
    }

    public void deleteEmployeeById(Long employeeId) {
        employeeExists(employeeId);
        employeeRepository.deleteById(employeeId);
    }

    public void employeeChangeStanding(Long employeeId) {

        employeeExists(employeeId);
        Employee employee = employeeRepository.getById(employeeId);
        Stand employeeStanding = employee.getStanding();

        if (employeeStanding == Stand.EMPLOYEE) {

            employee.setStanding(Stand.MANAGER);
            employeeRepository.save(employee);
            log.info("Employee with id: " + employee.getId() + " promoted");

        } else {

            employee.setStanding(Stand.EMPLOYEE);
            employeeRepository.save(employee);
            log.info("Employee with id: " + employee.getId() + " demoted");

        }
    }

    private void employeeExists(Long employeeId) {
        boolean exists = employeeRepository.existsById(employeeId);
        if (!exists) {
            throw new IllegalStateException("employee with id " + employeeId + " does not exists");
        }
    }

}
