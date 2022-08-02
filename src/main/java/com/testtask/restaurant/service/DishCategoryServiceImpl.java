package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.DishCategoryConversion;
import com.testtask.restaurant.entity.DishCategoryDAO;
import com.testtask.restaurant.repository.DishCategoryRepository;
import com.testtask.restaurant.transfer.DishCategoryTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {

    private final DishCategoryRepository dishCategoryRepository;

    @Autowired
    public DishCategoryServiceImpl(DishCategoryRepository dishCategoryRepository){
        this.dishCategoryRepository = dishCategoryRepository;
    }

    @Override
    @Transactional
    public List<DishCategoryTO> getDishCategories() {
        return dishCategoryRepository.findAll()
                .stream()
                .map(DishCategoryConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DishCategoryTO getDishCategoryById(int dishCategoryId) {
        return dishCategoryRepository.findById(dishCategoryId)
                .map(DishCategoryConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public DishCategoryTO addDishCategory(DishCategoryTO dishCategory) {
        if (dishCategoryRepository.existsByIdOrName(dishCategory.getId(), dishCategory.getName())) {
            throw new IllegalArgumentException(
                    String.format("DishCategory with id = %d or name = %s is already created",
                            dishCategory.getId(),
                            dishCategory.getName()));
        }
        return DishCategoryConversion.convertToTO(saveDishCategory(dishCategory));
    }

    @Override
    @Transactional
    public DishCategoryTO editDishCategory(DishCategoryTO dishCategory) {
        dishCategoryRepository.findById(dishCategory.getId());
        if (!dishCategoryRepository.existsById(dishCategory.getId())) {
            throw new IllegalArgumentException(
                    String.format("DishCategory with id = %d is non created",
                            dishCategory.getId()));
        }
        //Checking if another id has same name
        if (dishCategoryRepository.existsByNameAndIdNot(dishCategory.getName(), dishCategory.getId())) {
            throw new IllegalArgumentException(
                    String.format("DishCategory with name = %s is already created",
                            dishCategory.getName()));
        }
        return DishCategoryConversion.convertToTO(saveDishCategory(dishCategory));
    }

    private DishCategoryDAO saveDishCategory(DishCategoryTO dishCategory) {
        return dishCategoryRepository.save(DishCategoryConversion.convertToDTO(dishCategory));
    }

    @Override
    @Transactional
    public void deleteDishCategory(int dishCategoryId) {
        dishCategoryRepository.deleteById(dishCategoryId);
    }
}
