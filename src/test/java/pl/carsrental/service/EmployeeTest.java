package pl.carsrental.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.carsrental.employee.Employee;
import pl.carsrental.employee.EmployeeRepository;
import pl.carsrental.employee.EmployeeService;
import pl.carsrental.employee.Stand;

@SpringBootTest
public class EmployeeTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void changeStandingTestManager() {
        //given
        Employee employee1 = employeeRepository.save(Employee.builder()
                .id(1L)
                .firstName("Marcin")
                .surname("Nowacki")
                .standing(Stand.EMPLOYEE)
                .email("m.nowacki@wp.pl")
                .address("Gliwice")
                .build());
        //when
        employeeService.employeeChangeStanding(employee1);
        //then
        Assertions.assertEquals(employee1.getStanding(), Stand.MANAGER);
    }

    @Test
    public void changeStandingTestEmployee() {
        //given
        Employee employee1 = employeeRepository.save(Employee.builder()
                .id(1L)
                .firstName("Marcin")
                .surname("Nowacki")
                .standing(Stand.MANAGER)
                .email("m.nowacki@wp.pl")
                .address("Gliwice")
                .build());
        //when
        employeeService.employeeChangeStanding(employee1);
        //then
        Assertions.assertEquals(employee1.getStanding(), Stand.EMPLOYEE);
    }
}
