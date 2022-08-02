package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.DishConversion;
import com.testtask.restaurant.entity.DishCategoryDAO;
import com.testtask.restaurant.entity.DishDAO;
import com.testtask.restaurant.entity.IngredientDAO;
import com.testtask.restaurant.repository.DishCategoryRepository;
import com.testtask.restaurant.repository.DishRepository;
import com.testtask.restaurant.repository.IngredientRepository;
import com.testtask.restaurant.transfer.DishTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;
    private final DishCategoryRepository dishCategoryRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository,
                           IngredientRepository ingredientRepository,
                           DishCategoryRepository dishCategoryRepository){
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
        this.dishCategoryRepository = dishCategoryRepository;
    }

    @Override
    @Transactional
    public List<DishTO> getDishes() {
        return dishRepository.findAll()
                .stream()
                .map(DishConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DishTO getDishById(int dishId) {
        return dishRepository.findById(dishId)
                .map(DishConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public DishTO addDish(DishTO dish) {
        if (dishRepository.existsByIdOrName(dish.getId(), dish.getName())) {
            throw new IllegalArgumentException(
                    String.format("Dish with id = %d or name = %s is already created",
                            dish.getId(),
                            dish.getName()));
        }
        return DishConversion.convertToTO(saveDish(dish));
    }

    @Override
    @Transactional
    public DishTO editDish(DishTO dish) {
        if (!dishRepository.existsById(dish.getId())) {
            throw new IllegalArgumentException(
                    String.format("Dish with id = %d is non created",
                            dish.getId()));
        }
        //Checking if another id has same name
        if (dishRepository.existsByNameAndIdNot(dish.getName(), dish.getId())) {
            throw new IllegalArgumentException(
                    String.format("Dish with name = %s is already created",
                            dish.getName()));
        }
        return DishConversion.convertToTO(saveDish(dish));
    }

    private DishDAO saveDish(DishTO dish) {
        Set<IngredientDAO> ingredientDAOs = Optional.ofNullable(dish.getIngredientIds())
                .map(ingredientIds -> ingredientIds.stream()
                        .map(this::getIngredientDAO)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        Set<DishCategoryDAO> dishCategoryDAOs = Optional.ofNullable(dish.getDishCategoryIds())
                .map(dishCategoryIds -> dishCategoryIds.stream()
                        .map(this::getDishCategoryDAO)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        DishDAO dishDAO = DishConversion.convertToDTO(dish, ingredientDAOs, dishCategoryDAOs);
        return dishRepository.save(dishDAO);

    }

    @Override
    @Transactional
    public void deleteDish(int dishId) {
        dishRepository.deleteById(dishId);
    }

    private IngredientDAO getIngredientDAO(int ingredientId) {
        Optional<IngredientDAO> ingredientDAO = ingredientRepository.findById(ingredientId);
        return ingredientDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("Ingredient with id %d not found", ingredientId)
        ));
    }

    private DishCategoryDAO getDishCategoryDAO(int dishCategoryId) {
        Optional<DishCategoryDAO> dishCategoryDAO = dishCategoryRepository.findById(dishCategoryId);
        return dishCategoryDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("DishCategory with id %d not found", dishCategoryId)
        ));
    }
}
