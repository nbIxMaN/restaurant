package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.DishDAO;
import com.testtask.restaurant.entity.EmployeeDAO;
import com.testtask.restaurant.entity.OrderDAO;
import com.testtask.restaurant.transfer.OrderTO;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Conversion helper for Order
 */
public class OrderConversion {

    /**
     * Convert OrderDAO to OrderTO dish and employees will be converted to id
     * @param orderDAO order object from database
     * @return order object for transfer
     */
    public static OrderTO convertToTO(OrderDAO orderDAO) {
        OrderTO.OrderTOBuilder orderTOBuilder = OrderTO.builder();
        orderTOBuilder.id(orderDAO.getId());
        if (orderDAO.getDish() != null) {
            orderTOBuilder.dishId(orderDAO.getDish().getId());
        }
        orderTOBuilder.orderDate(orderDAO.getOrderDate());
        orderTOBuilder.preparationDate(orderDAO.getPreparationDate());
        orderTOBuilder.serviceDate(orderDAO.getServiceDate());
        if (!CollectionUtils.isEmpty(orderDAO.getEmployees())) {
            orderTOBuilder.employeeIds(orderDAO.getEmployees().stream()
                    .map(EmployeeDAO::getId)
                    .collect(Collectors.toList()));
        }
        return orderTOBuilder.build();
    }

    /**
     * Convert orderTO to OrderDAO
     * dish will be replaced by dishDAO
     * employees will be replaced by employeeDAOS
     * @param orderTO order object from transfer
     * @param dishDAO dish object from database
     * @param employeeDAOS employee objects from database
     * @return order object for database
     */
    public static OrderDAO convertToDTO(OrderTO orderTO, DishDAO dishDAO, Set<EmployeeDAO> employeeDAOS) {
        OrderDAO.OrderDAOBuilder orderDAOBuilder = OrderDAO.builder();
        orderDAOBuilder.id(orderTO.getId());
        orderDAOBuilder.orderDate(orderTO.getOrderDate());
        orderDAOBuilder.serviceDate(orderTO.getServiceDate());
        orderDAOBuilder.preparationDate(orderTO.getPreparationDate());
        if (dishDAO != null) {
            orderDAOBuilder.dish(dishDAO);
        }
        if (!CollectionUtils.isEmpty(employeeDAOS)) {
            orderDAOBuilder.employees(employeeDAOS);
        }
        return orderDAOBuilder.build();
    }

}
