package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.OrderTO;

import java.util.List;

public interface OrderService {

    List<OrderTO> getOrders();

    OrderTO getOrdersById(int orderId);

    OrderTO addOrder(OrderTO order);

    OrderTO editOrder(OrderTO order);

    void deleteOrder(int orderId);

}
