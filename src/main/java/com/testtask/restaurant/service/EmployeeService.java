package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.EmployeeTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeTO> getEmployees();

    EmployeeTO getEmployeeById(int employeeId);

    EmployeeTO addEmployee(EmployeeTO employee);

    EmployeeTO editEmployee(EmployeeTO employee);

    void deleteEmployee(int employeeId);

}
