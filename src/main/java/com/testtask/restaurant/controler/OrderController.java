package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.OrderService;
import com.testtask.restaurant.transfer.OrderTO;
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
@RequestMapping("/order")
@Tag(name = "Order", description = "Order API")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    @Operation(summary = "Get orders")
    public List<OrderTO> getOrders(){
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a order by id")
    public OrderTO getOrders(@PathVariable("id") int orderId){
        return orderService.getOrdersById(orderId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new order")
    public OrderTO addOrder(@RequestBody OrderTO orderTO) {
        return orderService.addOrder(orderTO);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing order")
    public OrderTO editOrder(@RequestBody OrderTO orderTO) {
        return orderService.editOrder(orderTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove order by id")
    public void removeOrder(@PathVariable("id") int orderId) {
        orderService.deleteOrder(orderId);
    }

}
