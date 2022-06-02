package pl.carsrental.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(path = "/admin/employees")
    public String getEmployee(ModelMap modelMap) {
        modelMap.addAttribute("employees", employeeService.getEmployees());
        return "employee-panel";
    }

    @GetMapping(path = "/admin/employee/create")
    public String showCreateEmployeeForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyEmployee", new Employee());
        return "employee-create";
    }

    @PostMapping(path = "/admin/employee/save")
    public String handleNewEmployee(@ModelAttribute("emptyEmployee") Employee employee) {
        employee.setStanding(Stand.EMPLOYEE);
        employeeService.save(employee);
        log.info("Handle new employee: " + employee);
        return "redirect:/admin/employees";
    }

    @PostMapping(path = "/admin/employee/update")
    public String handleUpdatedEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        log.info("Handle updated employee: " + employee);
        return "redirect:/admin/employees";
    }

    @GetMapping(path = "/admin/employee/edit/{employeeId}")
    public String showUpdateEmployeeForm(@PathVariable Long employeeId, ModelMap modelMap) {
        modelMap.addAttribute("employee", employeeService.getEmployeeById(employeeId));
        return "employee-edit";
    }

    @GetMapping(path = "/admin/employee/delete/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
        log.info("Deleted employee with id " + employeeId);
        return "redirect:/admin/employees";
    }

    @GetMapping(path = "/admin/employee/change/{employeeId}")
    public String changeEmployeeStanding(@PathVariable("employeeId") Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employeeService.employeeChangeStanding(employee);
        return "redirect:/admin/employees";
    }
}
