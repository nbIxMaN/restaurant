package com.testtask.restaurant.convertion;

import com.testtask.restaurant.entity.IngredientDAO;
import com.testtask.restaurant.entity.SupplierDAO;
import com.testtask.restaurant.transfer.SupplierTO;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Conversion helper for ingredient supplier
 */
public class SupplierConversion {

    /**
     * Convert SupplierDAO to SupplierTO ingredients will be converted to id
     * @param supplierDAO ingredient supplier object from database
     * @return ingredient supplier object for transfer
     */
    public static SupplierTO convertToTO(SupplierDAO supplierDAO) {
        SupplierTO.SupplierTOBuilder supplierTOBuilder = SupplierTO.builder();
        supplierTOBuilder.id(supplierDAO.getId());
        supplierTOBuilder.name(supplierDAO.getName());
        if (!CollectionUtils.isEmpty(supplierDAO.getIngredients())){
            supplierTOBuilder.ingredientIds(
                    supplierDAO.getIngredients().stream()
                            .map(IngredientDAO::getId)
                            .collect(Collectors.toList())
            );
        }
        return supplierTOBuilder.build();
    }

    /**
     * Convert SupplierTO to SupplierDAO
     * ingredients will be replaced by ingredientDAOSet
     * @param supplierTO ingredient supplier object from transfer
     * @param ingredientDAOSet ingredient objects from database
     * @return ingredient supplier object for database
     */
    public static SupplierDAO convertToDTO(SupplierTO supplierTO, Set<IngredientDAO> ingredientDAOSet) {
        SupplierDAO.SupplierDAOBuilder supplierDAOBuilder = SupplierDAO.builder();
        supplierDAOBuilder.id(supplierTO.getId());
        supplierDAOBuilder.name(supplierTO.getName());
        if (!CollectionUtils.isEmpty(ingredientDAOSet)) {
            supplierDAOBuilder.ingredients(ingredientDAOSet);
        }
        return supplierDAOBuilder.build();
    }

}
