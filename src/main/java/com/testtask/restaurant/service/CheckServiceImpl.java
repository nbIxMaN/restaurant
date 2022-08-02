package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.CheckConversion;
import com.testtask.restaurant.entity.CheckDAO;
import com.testtask.restaurant.entity.OrderDAO;
import com.testtask.restaurant.repository.CheckRepository;
import com.testtask.restaurant.repository.OrderRepository;
import com.testtask.restaurant.transfer.CheckTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public CheckServiceImpl(CheckRepository checkRepository,
                            OrderRepository orderRepository){
        this.checkRepository = checkRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public List<CheckTO> getChecks() {
        return checkRepository.findAll()
                .stream()
                .map(CheckConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CheckTO getCheckById(int checkId) {
        return checkRepository.findById(checkId)
                .map(CheckConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public CheckTO addCheck(CheckTO check) {
        if (checkRepository.existsById(check.getId())) {
            throw new IllegalArgumentException(String.format("Check with id = %d is already created", check.getId()));
        }
        return CheckConversion.convertToTO(saveCheck(check));
    }

    @Override
    @Transactional
    public CheckTO editCheck(CheckTO check) {
        if (!checkRepository.existsById(check.getId())) {
            throw new IllegalArgumentException(String.format("Check with id = %d is not created", check.getId()));
        }
        return CheckConversion.convertToTO(saveCheck(check));
    }

    private CheckDAO saveCheck(CheckTO check) {
        if (CollectionUtils.isEmpty(check.getOrdersIds())) {
            throw new IllegalArgumentException("Order list cannot be empty");
        }
        List<OrderDAO> orderDAO = Optional.ofNullable(check.getOrdersIds())
                .map(orderIds -> orderIds.stream()
                        .map(this::getOrderDAO)
                        .collect(Collectors.toList())
                ).orElse(Collections.emptyList());
        CheckDAO checkDAO = CheckConversion.convertToDTO(check, orderDAO);
        return checkRepository.save(checkDAO);
    }

    @Override
    @Transactional
    public void deleteCheck(int checkId) {
        checkRepository.deleteById(checkId);
    }

    private OrderDAO getOrderDAO(int orderId) {
        Optional<OrderDAO> orderDAO = orderRepository.findById(orderId);
        return orderDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("Order with id %d not found", orderId)
        ));
    }
}
