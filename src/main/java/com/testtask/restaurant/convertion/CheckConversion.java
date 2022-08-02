package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.CheckDAO;
import com.testtask.restaurant.entity.OrderDAO;
import com.testtask.restaurant.transfer.CheckTO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Conversion helper for Check
 */
public class CheckConversion {

    /**
     * Convert CheckDAO to CheckTO orders will be converted to ids
     * @param orderDAO check object from database
     * @return check object for transfer
     */
    public static CheckTO convertToTO(CheckDAO orderDAO) {
        CheckTO.CheckTOBuilder checkTOBuilder = CheckTO.builder();
        checkTOBuilder.id(orderDAO.getId());
        checkTOBuilder.date(orderDAO.getDate());
        if (!CollectionUtils.isEmpty(orderDAO.getOrders())) {
            checkTOBuilder.ordersIds(orderDAO.getOrders().stream()
                    .map(OrderDAO::getId)
                    .collect(Collectors.toList()));
        }
        return checkTOBuilder.build();
    }

    /**
     * Convert CheckTO to CheckDAO orders will be replaced by orderDAOList
     * @param checkTO check object from transfer
     * @param orderDAOList order objects from database
     * @return check object for database
     */
    public static CheckDAO convertToDTO(CheckTO checkTO, List<OrderDAO> orderDAOList) {
        CheckDAO.CheckDAOBuilder checkDAOBuilder = CheckDAO.builder();
        checkDAOBuilder.id(checkTO.getId());
        checkDAOBuilder.date(checkTO.getDate());
        if (!CollectionUtils.isEmpty(orderDAOList)) {
            checkDAOBuilder.orders(orderDAOList);
        }
        return checkDAOBuilder.build();
    }

}
