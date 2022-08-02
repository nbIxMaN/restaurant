package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.IngredientDAO;
import com.testtask.restaurant.entity.SupplierDAO;
import com.testtask.restaurant.transfer.IngredientTO;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Conversion helper for Ingredient
 */
public class IngredientConversion {

    /**
     * Convert IngredientDAO to IngredientTO suppliers will be converted to id
     * @param ingredientDAO ingredient object from database
     * @return ingredient object for transfer
     */
    public static IngredientTO convertToTO(IngredientDAO ingredientDAO) {
        IngredientTO.IngredientTOBuilder ingredientTOBuilder = IngredientTO.builder();
        ingredientTOBuilder.id(ingredientDAO.getId());
        ingredientTOBuilder.name(ingredientDAO.getName());
        if (!CollectionUtils.isEmpty(ingredientDAO.getSuppliers())) {
            ingredientTOBuilder.supplierIds(
                    ingredientDAO.getSuppliers().stream()
                            .map(SupplierDAO::getId)
                            .collect(Collectors.toList())
            );
        }
        return ingredientTOBuilder.build();
    }

    /**
     * Convert IngredientTO to IngredientDAO supplier will be replaced by supplierDAOS
     * @param ingredientTO ingredient object from transfer
     * @param supplierDAOS supplier objects from database
     * @return ingredient object for database
     */
    public static IngredientDAO convertToDTO(IngredientTO ingredientTO, Set<SupplierDAO> supplierDAOS) {
        IngredientDAO.IngredientDAOBuilder ingredientDAOBuilder = IngredientDAO.builder();
        ingredientDAOBuilder.id(ingredientTO.getId());
        ingredientDAOBuilder.name(ingredientTO.getName());
        if (!CollectionUtils.isEmpty(supplierDAOS)) {
            ingredientDAOBuilder.suppliers(supplierDAOS);
        }
        return ingredientDAOBuilder.build();
    }

}
