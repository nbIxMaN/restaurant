package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.DishCategoryDAO;
import com.testtask.restaurant.entity.DishDAO;
import com.testtask.restaurant.entity.IngredientDAO;
import com.testtask.restaurant.transfer.DishTO;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Conversion helper for Dish
 */
public class DishConversion {

    /**
     * Convert DishDAO to DishTO ingredients and dishCategories will be converted to ids
     * @param dishDAO dish object from database
     * @return dish object for transfer
     */
    public static DishTO convertToTO(DishDAO dishDAO) {
        DishTO.DishTOBuilder dishTOBuilder = DishTO.builder();
        dishTOBuilder.id(dishDAO.getId());
        dishTOBuilder.name(dishDAO.getName());
        dishTOBuilder.price(dishDAO.getPrice());
        if (!CollectionUtils.isEmpty(dishDAO.getIngredients())) {
            dishTOBuilder.ingredientIds(
                    dishDAO.getIngredients().stream()
                            .map(IngredientDAO::getId)
                            .collect(Collectors.toList())
            );
        }
        if (!CollectionUtils.isEmpty(dishDAO.getDishCategories())) {
            dishTOBuilder.dishCategoryIds(
                    dishDAO.getDishCategories().stream()
                            .map(DishCategoryDAO::getId)
                            .collect(Collectors.toList())
            );
        }
        return dishTOBuilder.build();
    }

    /**
     * Convert DishTO to DishDAO
     * ingredients will be replaced by ingredientDAOList
     * dishCategories will be replaced by dishCategoryDAOS
     * @param dishTO dish object from transfer
     * @param ingredientDAOList ingredient objects from database
     * @param dishCategoryDAOS dishCategory objects from database
     * @return dish object for transfer
     */
    public static DishDAO convertToDTO(DishTO dishTO,
                                       Set<IngredientDAO> ingredientDAOList,
                                       Set<DishCategoryDAO> dishCategoryDAOS) {
        DishDAO.DishDAOBuilder dishDAOBuilder = DishDAO.builder();
        dishDAOBuilder.id(dishTO.getId());
        dishDAOBuilder.name(dishTO.getName());
        dishDAOBuilder.price(dishTO.getPrice());
        if (!CollectionUtils.isEmpty(ingredientDAOList)) {
            dishDAOBuilder.ingredients(ingredientDAOList);
        }
        if (!CollectionUtils.isEmpty(dishCategoryDAOS)) {
            dishDAOBuilder.dishCategories(dishCategoryDAOS);
        }
        return dishDAOBuilder.build();
    }

}
