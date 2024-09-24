package com.ida.management.delivery;

import com.ida.management.entities.Department;
import com.ida.management.entities.DepartmentReport;
import com.ida.management.entities.Employee;
import com.ida.management.entities.Task;
import com.ida.management.service.DepartmentService;
import com.ida.management.service.EmployeeService;
import com.ida.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaskService taskService;

    // Get all employees in a specific department
    @GetMapping("/departments/{departmentId}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String departmentId) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        if (department.isPresent()) {
            List<Employee> employees = department.get().getEmployees();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get all tasks assigned to a specific employee
    @GetMapping("/employees/{employeeId}/tasks")
    public ResponseEntity<List<Task>> getTasksByEmployee(@PathVariable String employeeId) {
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        if (employee.isPresent()) {
            List<Task> tasks = employee.get().getTasks();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get a report of all departments and their employees
    @GetMapping("/report/departments")
    public ResponseEntity<List<DepartmentReport>> getDepartmentsReport() {
        List<Department> departments = departmentService.getAllDepartments();
        List<DepartmentReport> reports = departments.stream().map(department -> {
            DepartmentReport report = new DepartmentReport();
            report.setDepartmentName(department.getName());
            report.setEmployeeCount(department.getEmployees().size());
            return report;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // Bulk assign a task to multiple employees
    @PostMapping("/tasks/{taskId}/assign")
    public ResponseEntity<String> bulkAssignTaskToEmployees(
            @PathVariable String taskId,
            @RequestBody List<String> employeeIds) {
        Optional<Task> task = taskService.getTaskById(taskId);
        if (task.isPresent()) {
            for (String employeeId : employeeIds) {
                taskService.assignTaskToEmployee(taskId, employeeId);
            }
            return new ResponseEntity<>("Task assigned to multiple employees", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get the total number of employees across all departments
    @GetMapping("/employees/count")
    public ResponseEntity<Long> getTotalEmployeeCount() {
        long totalEmployees = employeeService.getAllEmployees().size();
        return new ResponseEntity<>(totalEmployees, HttpStatus.OK);
    }

    // Get the total number of tasks assigned across all employees
    @GetMapping("/tasks/count")
    public ResponseEntity<Long> getTotalTaskCount() {
        long totalTasks = taskService.getAllTasks().size();
        return new ResponseEntity<>(totalTasks, HttpStatus.OK);
    }
}
