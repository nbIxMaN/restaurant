package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.DishCategoryDAO;
import com.testtask.restaurant.transfer.DishCategoryTO;

/**
 * Conversion helper for Dish category
 */
public class DishCategoryConversion {

    /**
     * Convert DishCategoryDAO to DishCategoryTO
     * @param dishCategoryDAO dishCategory object from database
     * @return dishCategory object for transfer
     */
    public static DishCategoryTO convertToTO(DishCategoryDAO dishCategoryDAO) {
        DishCategoryTO.DishCategoryTOBuilder dishCategoryTOBuilder = DishCategoryTO.builder();
        dishCategoryTOBuilder.id(dishCategoryDAO.getId());
        dishCategoryTOBuilder.name(dishCategoryDAO.getName());
        return dishCategoryTOBuilder.build();
    }

    /**
     * Convert DishCategoryTO to DishCategoryDAO
     * @param dishCategoryTO dishCategory object from transfer
     * @return dishCategory object for database
     */
    public static DishCategoryDAO convertToDTO(DishCategoryTO dishCategoryTO) {
        DishCategoryDAO.DishCategoryDAOBuilder dishCategoryDAOBuilder = DishCategoryDAO.builder();
        dishCategoryDAOBuilder.id(dishCategoryTO.getId());
        dishCategoryDAOBuilder.name(dishCategoryTO.getName());
        return dishCategoryDAOBuilder.build();
    }

}
