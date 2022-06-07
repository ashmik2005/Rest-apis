package com.example.jbdl.restapis;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // To make this class visible to our servlet container
public class EmployeeController {

    private static int count = 1;
    private HashMap<Integer, Employee> employeeMap = new HashMap<>();

    /**

     Schema:
     HashMap : {employee.id, employee}

     * Add an employee --> input: employee, output: void
     * Get details of a particular employee --> input: employee.id, output: employee
     * Get details of all employees --> input: void, output: List<employee>
     * Update details of an employee --> input: employee.id, (details to be updated), output: void
     * Delete the employee --> input: employee.id, output: boolean | employee deleted | void
     **/

    // conversion from java to json and vice versa is done by some spring web utility

    @PostMapping("/employee/add")
    public Employee addEmployee(@RequestBody Employee employee) {

        employee.setId(count++);
        employeeMap.putIfAbsent(employee.getId(), employee);
        return employee;
    }

    @GetMapping("employee/get")
    public Employee getEmployee(@RequestParam("id") int id) {
        return Optional.ofNullable(employeeMap.get(id)).orElse(new Employee());
    }

    @GetMapping("employee/get/all")
    public List<Employee> getAllEmployees(){
        return employeeMap.values().stream().collect(Collectors.toList());
    }

    @PutMapping("employee/update")
    public Employee updateEmployee(@RequestBody Employee employee) throws Exception {


        if (employee.getId() == null) {
            throw new Exception("ID is not present for the employee to be updated");
        }

        if (!employeeMap.containsKey(employee.getId())) {
            throw new Exception("Employee does not exist");
        }

        employeeMap.put(employee.getId(), employee);
        return employee;

    }

    @DeleteMapping("employee/delete")
    public Employee deleteEmployee(@PathVariable("id") int id) {
        return  employeeMap.remove(id);
    }



}
