package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.OrderConversion;
import com.testtask.restaurant.entity.DishDAO;
import com.testtask.restaurant.entity.EmployeeDAO;
import com.testtask.restaurant.entity.OrderDAO;
import com.testtask.restaurant.repository.DishRepository;
import com.testtask.restaurant.repository.EmployeeRepository;
import com.testtask.restaurant.repository.OrderRepository;
import com.testtask.restaurant.transfer.OrderTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final DishRepository dishRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            EmployeeRepository employeeRepository,
                            DishRepository dishRepository){
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    @Transactional
    public List<OrderTO> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderTO getOrdersById(int orderId) {
        return orderRepository.findById(orderId)
                .map(OrderConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public OrderTO addOrder(OrderTO order) {
        if (orderRepository.existsById(order.getId())) {
            throw new IllegalArgumentException(
                    String.format("Order with id = %d is already created",
                            order.getId()));
        }
        return OrderConversion.convertToTO(saveOrder(order));
    }

    @Override
    @Transactional
    public OrderTO editOrder(OrderTO order) {
        if (!orderRepository.existsById(order.getId())) {
            throw new IllegalArgumentException(
                    String.format("Order with id = %d is non created",
                            order.getId()));
        }
        return OrderConversion.convertToTO(saveOrder(order));
    }

    private OrderDAO saveOrder(OrderTO order) {
        if (CollectionUtils.isEmpty(order.getEmployeeIds())) {
            throw new IllegalArgumentException("Employees list should not be empty");
        }
        if (order.getDishId() == 0) {
            throw new IllegalArgumentException("Dish should not be empty");
        }
        Set<EmployeeDAO> employeeDAOS = order.getEmployeeIds().stream()
                .map(this::getEmployeeDAO)
                .collect(Collectors.toSet());
        DishDAO dishDAO = getDishDAO(order.getDishId());
        OrderDAO orderDAO = OrderConversion.convertToDTO(order, dishDAO, employeeDAOS);
        return orderRepository.save(orderDAO);
    }

    @Override
    @Transactional
    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }

    private EmployeeDAO getEmployeeDAO(int employeeId) {
        Optional<EmployeeDAO> employeeDAO = employeeRepository.findById(employeeId);
        return employeeDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("Employee with id %d not found", employeeId)
        ));
    }

    private DishDAO getDishDAO(int dishId) {
        Optional<DishDAO> dishDAO = dishRepository.findById(dishId);
        return dishDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("Dish with id %d not found", dishId)
        ));
    }
}
