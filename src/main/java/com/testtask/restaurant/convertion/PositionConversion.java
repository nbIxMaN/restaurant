package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.PositionDAO;
import com.testtask.restaurant.transfer.PositionTO;

/**
 * Conversion helper for employee position
 */
public class PositionConversion {

    /**
     * Convert PositionDAO to PositionTO
     * @param positionDAO employee position object from database
     * @return employee position object for transfer
     */
    public static PositionTO convertToTO(PositionDAO positionDAO) {
        PositionTO.PositionTOBuilder positionTOBuilder = PositionTO.builder();
        positionTOBuilder.id(positionDAO.getId());
        positionTOBuilder.name(positionDAO.getName());
        return positionTOBuilder.build();
    }

    /**
     * Convert PositionTO to PositionDAO
     * @param positionTO employee position object from transfer
     * @return employee position object for database
     */
    public static PositionDAO convertToDTO(PositionTO positionTO) {
        PositionDAO.PositionDAOBuilder positionDAOBuilder = PositionDAO.builder();
        positionDAOBuilder.id(positionTO.getId());
        positionDAOBuilder.name(positionTO.getName());
        return positionDAOBuilder.build();
    }

}
