package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.EmployeeDAO;
import com.testtask.restaurant.entity.PositionDAO;
import com.testtask.restaurant.transfer.EmployeeTO;

/**
 * Conversion helper for Employee
 */
public class EmployeeConversion {

    /**
     * Convert EmployeeDAO to EmployeeTO position will be converted to id
     * @param employeeDAO employee object from database
     * @return employee object for transfer
     */
    public static EmployeeTO convertToTO(EmployeeDAO employeeDAO) {
        EmployeeTO.EmployeeTOBuilder employeeTOBuilder = EmployeeTO.builder();
        employeeTOBuilder.id(employeeDAO.getId());
        employeeTOBuilder.name(employeeDAO.getName());
        employeeTOBuilder.salary(employeeDAO.getSalary());
        if (employeeDAO.getPosition() != null) {
            employeeTOBuilder.positionId(employeeDAO.getPosition().getId());
        }
        return employeeTOBuilder.build();
    }

    /**
     * Convert EmployeeTO to EmployeeDAO position will be replaced by positionDAO
     * @param employeeTO employee object from transfer
     * @param positionDAO position object from database
     * @return position object for database
     */
    public static EmployeeDAO convertToDTO(EmployeeTO employeeTO, PositionDAO positionDAO) {
        EmployeeDAO.EmployeeDAOBuilder employeeDAOBuilder = EmployeeDAO.builder();
        employeeDAOBuilder.id(employeeTO.getId());
        employeeDAOBuilder.name(employeeTO.getName());
        employeeDAOBuilder.salary(employeeTO.getSalary());
        if (positionDAO != null) {
            employeeDAOBuilder.position(positionDAO);
        }
        return employeeDAOBuilder.build();
    }

}
