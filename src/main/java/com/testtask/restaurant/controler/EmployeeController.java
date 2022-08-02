package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.EmployeeService;
import com.testtask.restaurant.transfer.EmployeeTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee", description = "Employee API")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    @Operation(summary = "Get employees")
    public List<EmployeeTO> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a employee by id")
    public EmployeeTO getEmployeeById(@PathVariable("id") int employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new employee")
    public EmployeeTO addEmployee(@RequestBody EmployeeTO employeeTO) {
        return employeeService.addEmployee(employeeTO);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing employee")
    public EmployeeTO editEmployee(@RequestBody EmployeeTO employeeTO) {
        return employeeService.editEmployee(employeeTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove employee by id")
    public void removeEmployee(@PathVariable("id") int employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

}
